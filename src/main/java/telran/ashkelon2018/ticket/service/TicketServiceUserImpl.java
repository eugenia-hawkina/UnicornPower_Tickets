package telran.ashkelon2018.ticket.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.dao.ArchivedEventRepository;
import telran.ashkelon2018.ticket.dao.CancelledEventRepository;
import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;

@Service
public class TicketServiceUserImpl implements TicketServiceUser {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	CancelledEventRepository cancelledEventRepository;
	
	@Autowired
	ArchivedEventRepository archivedEventRepository;

	@Override
	public Set<Event> receiveUpcomingEvents(int page, int size) {
		// TODO Auto-generated method stub
		Set<Event> upcomingEvents = new HashSet<>();
		upcomingEvents.addAll(eventRepository.findAllBy()
			.skip(size*(page-1))
			.limit(size)
			.collect(Collectors.toSet()));
		return upcomingEvents;
	}

	@Override
	public Set<Event> receiveArchivedEvents(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Event> receiveEventsByDate(LocalDate from, LocalDate to, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Event> receiveEventsByHall(String hallId, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Event> receiveEventsByArtist(String artist, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seat buyTicket(EventId eventId, SeatId seatId, String login) {
		// TODO Auto-generated method stub
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
