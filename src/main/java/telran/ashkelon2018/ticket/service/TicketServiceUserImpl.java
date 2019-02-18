package telran.ashkelon2018.ticket.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import telran.ashkelon2018.ticket.exceptions.BadRequestException;
import telran.ashkelon2018.ticket.exceptions.NotFoundException;

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
			eventsAll.addAll(eventArchivedRepository
					.findByEventIdEventStartBetween(dateFrom, dateTo)
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
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
	public Seat buyTicket(EventId eventId, SeatId seatId, String login) {
		// FIXME=TODO
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event == null) {
			throw new BadRequestException("No event found");
		}
		Set <Seat> seats = event.getSeats();
		
//		if(seats.get(seatId).isAvailability()) {
//			
//		}
		return null;
	}

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
