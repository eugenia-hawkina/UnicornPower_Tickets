package telran.ashkelon2018.ticket.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.dao.EventArchivedRepository;
import telran.ashkelon2018.ticket.dao.EventCancelledRepository;
import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.dao.HallRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.dto.EventListByHallDateDto;
import telran.ashkelon2018.ticket.dto.TicketPurchaseDto;
import telran.ashkelon2018.ticket.exceptions.BadRequestException;
import telran.ashkelon2018.ticket.exceptions.NotFoundException;
import telran.ashkelon2018.ticket.exceptions.SeatNotAvailableException;

@Service
public class TicketServiceUserImpl implements TicketServiceUser {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventCancelledRepository eventCancelledRepository;
	
	@Autowired
	EventArchivedRepository eventArchivedRepository;
	
	@Autowired
	HallRepository hallRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Set<Event> receiveUpcomingEvents(int page, int size) {
		Set<Event> upcomingEvents = new HashSet<>();
		upcomingEvents.addAll(eventRepository.findAllBy()
			.skip(size*(page-1))
			.limit(size)
			.collect(Collectors.toSet()));
		return upcomingEvents;
	}

	@Override
	public Set<EventArchived> receiveArchivedEvents(int page, int size) {
		Set<EventArchived> archivedEvents = new HashSet<>();
		archivedEvents.addAll(eventArchivedRepository.findAllBy()
			.skip(size*(page-1))
			.limit(size)
			.collect(Collectors.toSet()));
		return archivedEvents;
	}

	@Override
	public Set<Event> receiveEventsByDate(EventListByHallDateDto filter, int page, int size) {
		Set<Event> eventsAll = new HashSet<>();
		Set<EventArchived> eventsArchivedAll = new HashSet<>();
		LocalDate dateFrom = filter.getDateFrom();
		LocalDate dateTo = filter.getDateTo();
		if(dateFrom == null || dateTo == null) {
			throw new BadRequestException("I need two dates");
		}
		if(dateTo.isBefore(dateFrom)) {
			LocalDate tmp = dateFrom;
			dateFrom = dateTo;
			dateTo = tmp;
		}
		if(dateFrom.isBefore(LocalDate.now()) && dateTo.isBefore(LocalDate.now())) {			
			eventsArchivedAll.addAll(eventArchivedRepository
					.findByEventIdEventStartBetween(dateFrom, dateTo)
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
			eventsAll = eventsArchivedAll.stream()
					.map(e -> convertArchivedEventToEvent(e))
					.collect(Collectors.toSet());		
		}
		if (dateFrom.isAfter(LocalDate.now()) && dateTo.isAfter(LocalDate.now())) {			
			eventsAll.addAll(eventRepository
					.findByEventIdEventStartBetween(dateFrom, dateTo)
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
		}
		// FIXME combine info from two repositories
		if(dateFrom.isBefore(LocalDate.now()) && dateTo.isAfter(LocalDate.now())) {
			throw new BadRequestException("Both dates should be either beforeNow or afterNow");
		}	
		
		return eventsAll;
	}

	private Event convertArchivedEventToEvent(EventArchived e) {
		return 	Event.builder()
					.eventStatus(e.getEventStatus())
					.eventName(e.getEventName())
					.artist(e.getArtist())
					.eventId(e.getEventId())
					.eventDurationMinutes(e.getEventDurationMinutes())
					.seats(e.getSeats())
					.eventType(e.getEventType())
					.description(e.getDescription())
					.images(e.getImages())
					.cancellationReason(e.getCancellationReason())
					.userId(e.getUserId())
					.build();
	}

	@Override
	public Set<Event> receiveEventsByHall(String hallId, int page, int size) {
		Set<Event> events = new HashSet<>();
		if(!hallRepository.existsById(hallId)) {
			throw new NotFoundException("Hall not found");
		}
		events.addAll(eventRepository.findByEventIdHallId(hallId)
				.skip(size*(page-1))
				.limit(size)
				.collect(Collectors.toSet()));		
		return events;
	}

	@Override
	public Set<Event> receiveEventsByArtist(String artist, int page, int size) {
		Set<Event> events = new HashSet<>();
		events.addAll(eventRepository.findByArtist(artist)
				.skip(size*(page-1))
				.limit(size)
				.collect(Collectors.toSet()));		
		return events;
	}
	
	@Override
	public boolean bookTicket(TicketPurchaseDto ticketPurchaseDto) {
		// FIXME=TODO
		EventId eventId = ticketPurchaseDto.getEventId();
		String login = ticketPurchaseDto.getLogin();
		Set<Seat> seats = ticketPurchaseDto.getSeats();
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event == null) {
			throw new BadRequestException("Event not found");
		}
			// check seat if available, change its availability, add buyer		
		Seat[] seatsArr = seats.stream().toArray(s -> new Seat[s]);
		for (int i = 0; i < seatsArr.length; i++) {			 
			Query query = new Query();
			Criteria criteria = Criteria.where("_id").is(eventId).and("seats").is(seatsArr[i]);
			query.addCriteria(criteria);
			Update update = new Update();
			update.set("seats.$.availability", "false").set("seats.$.buyerInfo", login);
//			Event eventCheck = new Event();
			event = mongoTemplate.findAndModify(query, update, Event.class);
			
			// if seat not available -> roll back
			if(event == null) {				
				for( int j = 0; j < i; j++) {
					seatsArr[j].setAvailability(false);
					seatsArr[j].setBuyerInfo(login);
					Query query2 = new Query();
					System.out.println(seatsArr[j].toString());
					Criteria criteria2 = Criteria.where("_id").is(eventId).and("seats").is(seatsArr[j]);
					query2.addCriteria(criteria2);
					update.set("seats.$.availability", "true").set("seats.$.buyerInfo", "free");
					event = mongoTemplate.findAndModify(query2, update, Event.class);
					System.out.println("find and modify");
				}
				throw new SeatNotAvailableException("Seat not available");
			} else {
				System.out.println("else");
			//	event = eventCheck;
			}
			
			// thread sleep 10 min -> roll back // timestamp
			TaskImpl task = new TaskImpl(seatsArr[i], eventId, login);
			Thread thread = new Thread(task);
			thread.start();
		}	
		return true;
	}
	
	private class TaskImpl implements Runnable{
	 
		Seat seat;
		EventId eventId;
		String login;
		
		private TaskImpl(Seat seat, EventId eventId, String login) {
			this.seat = seat;
			this.eventId = eventId;
			this.login = login;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(10000);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			seat.setAvailability(false);
			seat.setBuyerInfo(login);
			Query query2 = new Query();
			System.out.println(seat.toString());
			Criteria criteria2 = Criteria.where("_id").is(eventId).and("seats").is(seat);
			query2.addCriteria(criteria2);
			Update update = new Update();
			update.set("seats.$.availability", "true").set("seats.$.buyerInfo", "free");
						
			Event event = eventRepository.findById(eventId).orElse(null);
			event = mongoTemplate.findAndModify(query2, update, Event.class);

//			Query query = new Query();
//			Criteria criteria = Criteria.where("_id").is(eventId).and("seats").is(seat);
//			query.addCriteria(criteria);
//			update.set("seats.$.availability", "true").set("seats.$.buyerInfo", "free");
//			System.out.println(event.toString());
//			System.out.println();
//			event = mongoTemplate.findAndModify(query, update, Event.class);
//			System.out.println(event.toString());
		}	
	}

//	public void cancelTmpBookedTicket(EventId eventId, Seat seat) {
//		// FIXME - why unused
//		@SuppressWarnings("unused") 
//		Event event = eventRepository.findById(eventId).orElse(null);		
//		Query query = new Query();
//		Criteria criteria = Criteria.where("_id").is(eventId).and("seats").is(seat);
//		query.addCriteria(criteria);
//		Update update = new Update();
//		update.set("seats.$.availability", "true").set("seats.$.buyerInfo", "free");
//		System.out.println(event.toString());
//		System.out.println();
//		event = mongoTemplate.findAndModify(query, update, Event.class);
//		System.out.println(event.toString());
//	}
	
	@Override
	public Set<Event> receiveVisitedEvents(String login, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seat printTicket(SeatId seatId, String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seat discardTicket(SeatId seatId, String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
