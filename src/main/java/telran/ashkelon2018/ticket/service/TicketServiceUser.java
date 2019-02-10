package telran.ashkelon2018.ticket.service;

import java.time.LocalDate;
import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;

public interface TicketServiceUser {
	
	Set<Event> receiveUpcomingEvents(int page, int size);
	
	Set<Event> receiveArchivedEvents(int page, int size);
	
	Set<Event> receiveEventsByDate(LocalDate from, LocalDate to, int page, int size);
	
	Set<Event> receiveEventsByHall(String hallId, int page, int size);
	
	Set<Event> receiveEventsByArtist(String artist, int page, int size);
	
	Seat buyTicket(EventId eventId, SeatId seatId, String login);
	
	
	// for registered only!!!
	Set<Event> receiveVisitedEvents(String login, int page, int size);	
	
	Seat printTicket(SeatId seatId, String login);
	
	Seat discardTicket(SeatId seatId, String login);

}
