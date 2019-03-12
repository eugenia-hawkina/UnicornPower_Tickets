package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.EventListByHallDateDto;
import telran.ashkelon2018.ticket.dto.EventSearchDto;
import telran.ashkelon2018.ticket.dto.TicketBookingDto;
import telran.ashkelon2018.ticket.dto.TicketPayDto;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;
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
	UserAccountRepository userAccountRepository;
	
	@Autowired
	HallRepository hallRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Set<Event> receiveUpcomingEvents(int page, int size) {
		Set<Event> upcomingEvents = new HashSet<>();
		upcomingEvents.addAll(eventRepository.findAllBy()
			.filter(e -> e.getEventStatus().equals(EventStatus.ACTIVE))
			.skip(size*(page-1))
			.limit(size)
			.collect(Collectors.toSet()));
		return upcomingEvents;
	}

	@Override
	public Set<EventArchived> receiveArchivedEvents(int page, int size) {
		Set<EventArchived> archivedEvents = new HashSet<>();
		archivedEvents.addAll(eventArchivedRepository.findAllBy()
			.sorted((e1, e2) -> e2.getEventId().getEventStart()
					.compareTo(e1.getEventId().getEventStart()))
			.skip(size*(page-1))
			.limit(size)
			.collect(Collectors.toSet()));
		return archivedEvents;
	}

	@Override
	public Set<Event> receiveEventsByDate(EventListByDateDto filter, int page, int size) {
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
					.findByEventIdEventStartBetweenOrderByEventIdEventStart(dateFrom, dateTo)	
					.filter(e -> e.getEventStatus().equals(EventStatus.ACTIVE))
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
		}
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
		events.addAll(eventRepository.findByEventIdHallIdOrderByEventIdEventStart(hallId)
				.filter(e -> e.getEventStatus().equals(EventStatus.ACTIVE))
				.skip(size*(page-1))
				.limit(size)
				.collect(Collectors.toSet()));		
		return events;
	}

	@Override
	public Set<Event> receiveEventsByArtist(String artist, int page, int size) {
		Set<Event> events = new HashSet<>();
		events.addAll(eventRepository.findByArtistOrderByEventIdEventStart(artist)
				.filter(e -> e.getEventStatus().equals(EventStatus.ACTIVE))
				.skip(size*(page-1))
				.limit(size)
				.collect(Collectors.toSet()));	
		if(events.size() == 0) {
			throw new NotFoundException("Artist not found");
		}
		return events;
	}
	
	@Override
	public boolean bookTicket(TicketBookingDto ticketPurchaseDto) {
		EventId eventId = ticketPurchaseDto.getEventId();
		LocalDateTime startTime = eventId.getEventStart();
		if(startTime.isBefore(LocalDateTime.now().minusMinutes(120))) {
			throw new SeatNotAvailableException("Less than 2 hours before event, tickets available only in ticket office");
		};
		String login = ticketPurchaseDto.getLogin().toLowerCase();
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
			update.set("seats.$.availability", false).set("seats.$.buyerInfo", login);
			Event eventCheck = new Event();
			eventCheck = mongoTemplate.findAndModify(query, update, Event.class);
			
			// if seat not available -> roll back
			if(eventCheck == null) {				
				for( int j = 0; j < i; j++) {
					seatsArr[j].setAvailability(false);
					seatsArr[j].setBuyerInfo(login);
					Query query2 = new Query();
					Criteria criteria2 = Criteria.where("_id").is(eventId).and("seats").is(seatsArr[j]);
					query2.addCriteria(criteria2);
					update.set("seats.$.availability", true).set("seats.$.buyerInfo", "free");
					event  = mongoTemplate.findAndModify(query2, update, Event.class);
				}
				throw new SeatNotAvailableException("Seat not available");
			} 
		}
		
			// thread sleep 10 min -> roll back // timestamp
		for(int i = 0; i < seatsArr.length; i++) {		
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
			this.login = login.toLowerCase();
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(30000);	
				Event event = eventRepository.findById(eventId).orElse(null);
				seat.setAvailability(false);
				seat.setBuyerInfo(login);
				seat.setPaid(false);
				Query query = new Query();
				Criteria criteria = Criteria.where("_id").is(eventId).and("seats").is(seat);
				query.addCriteria(criteria);
				event = mongoTemplate.findOne(query, Event.class);
				if (event != null) {
				// if not paid -> roll back
					Query queryDel = new Query();
					Criteria criteriaDel = Criteria.where("_id").is(eventId).and("seats").is(seat);
					queryDel.addCriteria(criteriaDel);
					Update updateDel = new Update();
					updateDel.set("seats.$.availability", true).set("seats.$.buyerInfo", "free");
					event = mongoTemplate.findAndModify(queryDel, updateDel, Event.class);
					
				} else {
				//if paid -> add bookingTime				
					seat.setAvailability(false);
					seat.setBuyerInfo(login);
					seat.setPaid(true);
					Query queryUpd = new Query();
					Criteria criteriaUpd = Criteria.where("_id").is(eventId).and("seats").is(seat);
					queryUpd.addCriteria(criteriaUpd);
					Update updateUpd = new Update();
					LocalDateTime ldt = LocalDateTime.now();	
					ZonedDateTime zdt = ldt.atZone(ZoneId.of("GMT+03:00"));
					long time = zdt.toInstant().toEpochMilli();
					updateUpd.set("seats.$.bookingTime", time);
					event = mongoTemplate.findAndModify(queryUpd, updateUpd, Event.class);
					System.out.println(event.toString());
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean payTicket(TicketPayDto ticketPayDto) {
		EventId eventId = ticketPayDto.getEventId();
		String login = ticketPayDto.getLogin().toLowerCase();
		Set<Seat> seats = ticketPayDto.getSeats();
		boolean paid = ticketPayDto.isPaid();
		if(!paid) {
			return false;
		}
		eventRepository.findById(eventId).orElse(null);
		Seat[] seatsArr = seats.stream().toArray(s -> new Seat[s]);
		for (int i = 0; i < seatsArr.length; i++) {			 
			Query query = new Query();
			Criteria criteria = Criteria.where("_id").is(eventId).and("seats").is(seatsArr[i]);
			query.addCriteria(criteria);
			Update update = new Update();
			update.set("seats.$.paid", true);	
			mongoTemplate.findAndModify(query, update, Event.class);
		}
		UserAccount user = userAccountRepository.findById(login).orElse(null);
		if(user == null) {
			return true;
		}
		user.addVisitedEvent(eventId);
		userAccountRepository.save(user);	
		return true;
	}
	
	@Override
	public Set<Event> receiveVisitedEvents(Principal principal, int page, int size) {
		String id = principal.getName();
		UserAccount user = userAccountRepository.findById(id).orElse(null);
		if (user == null) {
			throw new NotFoundException("User not found");
		}
		Set<Event> events = new HashSet<Event>();
		Set<EventId> eventIds = user.getVisitedEvents();
		if(eventIds == null) {
			throw new NotFoundException("User didn't visit any events");
		}
		EventId[] arr = eventIds.toArray(new EventId[eventIds.size()]);
		for (int i = 0; i < eventIds.size(); i++) {
			System.out.println(arr[i]);
			if(arr[i].getEventStart().isBefore(LocalDateTime.now())) {
				EventArchived eventArchived = eventArchivedRepository.findById(arr[i]).orElse(null);
				events.add(convertArchivedEventToEvent(eventArchived));
			} else {
				events.add(eventRepository.findById(arr[i]).orElse(null));
			}
		}
		return events.stream()
				.sorted((e1, e2) -> e1.getEventId().getEventStart()
						.compareTo(e2.getEventId().getEventStart()))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Seat> getTickets(EventId eventId, Principal principal) {
		String login = principal.getName().toLowerCase();
		UserAccount user = userAccountRepository.findById(login).orElse(null);
		Set<EventId> eventIds = user.getVisitedEvents();
		if(!eventIds.contains(eventId)) {
			throw new NotFoundException("User didn't visit this event"); 
		}
		Event event = new Event();
		if(eventId.getEventStart().isBefore(LocalDateTime.now())) {
			event = convertArchivedEventToEvent(eventArchivedRepository.findById(eventId).orElse(null));
		} else {
			event = eventRepository.findById(eventId).orElse(null);
		}
		Set<Seat> seats = event.getSeats().stream()
				.filter(s -> s.getBuyerInfo().equals(login))
				.collect(Collectors.toSet());
		if(seats.size() == 0) {
			throw new NotFoundException("User didn't visit this event");
		}
		return seats;
	}

	@Override
	public Event receiveEventInfo(EventId eventId) {
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event == null) {
			throw new NotFoundException("Event not found");
		}
		return event;
	}
 
//	@Override
//	public Set<Seat> discardTickets(EventId eventId, Set<Seat> seats, String login) {
//		// Auto-generated method stub
//		return null;
//	}

	@Override
	public Set<Event> receiveEventsByHallAndDate(EventListByHallDateDto filter, int page, int size) {
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
		String hallId = filter.getHallId();
		if(dateFrom.isBefore(LocalDate.now()) && dateTo.isBefore(LocalDate.now())) {			
			eventsArchivedAll.addAll(eventArchivedRepository
					.findByEventIdHallIdAndEventIdEventStartBetween(hallId, dateFrom, dateTo)
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
			eventsAll = eventsArchivedAll.stream()
					.map(e -> convertArchivedEventToEvent(e))
					.collect(Collectors.toSet());
		}
		if (dateFrom.isAfter(LocalDate.now()) && dateTo.isAfter(LocalDate.now())) {			
			eventsAll.addAll(eventRepository
					.findByEventIdHallIdAndEventIdEventStartBetweenOrderByEventIdEventStart(hallId, dateFrom, dateTo)
					.filter(e -> e.getEventStatus().equals(EventStatus.ACTIVE))
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
		}
		if(dateFrom.isBefore(LocalDate.now()) && dateTo.isAfter(LocalDate.now())) {
			throw new BadRequestException("Both dates should be either beforeNow or afterNow");
		}
		return eventsAll;
	}
	
	public Set<Event> receiveEventsByEventType(EventType eventType, int page, int size){
		Set<Event> events = new HashSet<>();
		events.addAll(eventRepository				
				.findByEventTypeOrderByEventIdEventStart(eventType)
				.filter(e -> e.getEventStatus().equals(EventStatus.ACTIVE))
				.skip(size * (page - 1))
				.limit(size)
				.collect(Collectors.toSet()));
		return events;
	}
	
	public 	List<Event> searchEvents(EventSearchDto eventSearchDto, int page, int size){
	//	Set<Event> events = new HashSet<>();
		List<Event> events = new ArrayList<>();
		LocalDate dateFrom = eventSearchDto.getDateFrom();
		LocalDate dateTo = eventSearchDto.getDateTo();
		String artist = eventSearchDto.getArtist();
		EventType eventType = eventSearchDto.getEventType();
		String hallId = eventSearchDto.getHallId();
		Query query = new Query();
		query.addCriteria(Criteria.where("eventStatus").regex("^A"));		
		if(dateFrom != null && dateTo != null) {
			query.addCriteria(Criteria.where("_id.eventStart").gte(dateFrom).lte(dateTo));
		} 
		// FIXME date from not included
		if(dateFrom != null && dateTo == null) {
			query.addCriteria(Criteria.where("_id.eventStart").gte(dateFrom));
		}
		if(dateFrom == null && dateTo != null) {
			query.addCriteria(Criteria.where("_id.eventStart").lte(dateTo));
		}
		if(artist != null && artist != "") {
			query.addCriteria(Criteria.where("artist").regex("/*" + artist + "/*","i"));
		}
		if(eventType != null) {
			query.addCriteria(Criteria.where("eventType").is(eventType));
		}
		if(hallId != null) { 
			query.addCriteria(Criteria.where("_id.hallId").is(hallId));
		}
		List <EventArchived> eventArc = mongoTemplate.find(query, EventArchived.class);		
		events = mongoTemplate.find(query, Event.class);
	
		ArrayList<Event> result = new ArrayList<Event>(events.size() + eventArc.size());
        result.addAll(events);
        List<Event> events2 = eventArc.stream()
        	.map(e -> convertArchivedEventToEvent(e))
        	.collect(Collectors.toList());
        result.addAll(events2);
		return result.stream()
				.sorted((e1, e2) -> 
				(e1.getEventId().getEventStart()).compareTo(e2.getEventId().getEventStart()))
				.skip(size * (page - 1))
				.limit(size)
				.collect(Collectors.toList());
	}

}
