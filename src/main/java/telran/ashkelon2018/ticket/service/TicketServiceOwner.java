package telran.ashkelon2018.ticket.service;

import java.util.Set;

import org.springframework.security.core.userdetails.User;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;

public interface TicketServiceOwner {
	// FIXME returns DTO
	User findUser(String login);
	
	User addHallToManager(String hallId);
	
	User removeManagerRole(String login);
	
	Set<Event> receiveHiddenEvents();
	
	Event approveEvent(EventId eventId);
	
	Seat generateTicket(SeatId seatId, String login);
	
	Seat discardTicket(SeatId seatId, String login);

}
