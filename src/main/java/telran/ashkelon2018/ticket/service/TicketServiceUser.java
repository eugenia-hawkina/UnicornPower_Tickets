package telran.ashkelon2018.ticket.service;

import java.time.LocalDate;
import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;

public interface TicketServiceUser {
	
	Set<Event> recieveUpcomingEvents();
	
	Set<Event> receiveArchivedEvents();
	
	Set<Event> receiveEventsByDate(LocalDate from, LocalDate to);
	
	Set<Event> receiveEventsByHall(String hallId);
	
	Set<Event> receiveEventsByArtist(String artist);
	
	Seat buyTicket(SeatId seatId, String login);
	
	
	// for registered only!!!
	Set<Event> receiveVisitedEvents(String login);	
	
	Seat generateTicket(SeatId seatId, String login);
	
	Seat discardTicket(SeatId seatId, String login);

}
