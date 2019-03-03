package telran.ashkelon2018.ticket.service;

import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.TicketBookingDto;
import telran.ashkelon2018.ticket.dto.TicketPayDto;

public interface TicketServiceUser {
	
	Set<Event> receiveUpcomingEvents(int page, int size);
	
	Set<EventArchived> receiveArchivedEvents(int page, int size);
	
	Set<Event> receiveEventsByDate(EventListByDateDto filter, int page, int size);
	
	Set<Event> receiveEventsByHall(String hallId, int page, int size);
	
	Set<Event> receiveEventsByArtist(String artist, int page, int size);
	
	boolean bookTicket(TicketBookingDto ticketPurchaseDto);
	
	boolean payTicket(TicketPayDto ticketPayDto);
	
	
	// for registered only!!!
	Set<Event> receiveVisitedEvents(String login, int page, int size);	
	
	Seat printTicket(SeatId seatId, String login);
	
	Seat discardTicket(SeatId seatId, String login);

}
