package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.EventListByHallDateDto;
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

	Event receiveEventInfo(EventId eventId);
	
	Set<Event> receiveEventsByHallAndDate(EventListByHallDateDto filter, int page, int size);

	
	// for registered only!!!
	
	
		
	Set<Event> receiveVisitedEvents(Principal principal, int page, int size);	
	
	Set<Seat> getTickets(EventId eventId, Principal principal);
	
//	Set<Seat> discardTickets(EventId eventId, Set<Seat> seats, String login);

}
