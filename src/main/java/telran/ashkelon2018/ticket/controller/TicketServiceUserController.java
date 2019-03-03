package telran.ashkelon2018.ticket.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.TicketBookingDto;
import telran.ashkelon2018.ticket.dto.TicketPayDto;
import telran.ashkelon2018.ticket.service.TicketServiceUser;

@RestController
@RequestMapping("/user")
public class TicketServiceUserController {
	
	@Autowired
	TicketServiceUser ticketServiceUser;

	
	@GetMapping("/events")
	public Set<Event> receiveUpcomingEvents(@RequestParam int page, @RequestParam int size){
		return ticketServiceUser.receiveUpcomingEvents(page, size);
	}
	
	@GetMapping("/events/archive")
	public Set<EventArchived> receiveArchivedEvents(@RequestParam int page, @RequestParam int size){
		return ticketServiceUser.receiveArchivedEvents(page, size);
	}
	
	@PostMapping("/events/dates")
	public Set<Event> receiveEventsByDate(@RequestBody EventListByDateDto filter,
			@RequestParam int page, @RequestParam int size){
		return ticketServiceUser.receiveEventsByDate(filter, page, size);
	}
	
	@GetMapping("/events/hall/{hallId}")
	public Set<Event> receiveEventsByHall(@PathVariable String hallId, @RequestParam int page, @RequestParam int size){
		return ticketServiceUser.receiveEventsByHall(hallId, page, size);
	}
	
	@GetMapping("/events/artist/{artist}")
	public Set<Event> receiveEventsByArtist(@PathVariable String artist, @RequestParam int page, @RequestParam int size){
		return ticketServiceUser.receiveEventsByArtist(artist, page, size);
	}
	
	@PutMapping("/event/ticket/booking")
	public boolean bookTicket(@RequestBody TicketBookingDto ticketPurchaseDto) {
		return ticketServiceUser.bookTicket(ticketPurchaseDto);
	}
	
	@PutMapping("/event/ticket/pay")
	public boolean payTicket(@RequestBody TicketPayDto ticketPayDto) {
		return ticketServiceUser.payTicket(ticketPayDto);
	}
	
	// for registered only!!!
	@GetMapping("/events/visited")
	Set<Event> receiveVisitedEvents(Principal principal, @RequestParam int page, @RequestParam int size){
		return ticketServiceUser.receiveVisitedEvents(principal, page, size);
	}
	
	@GetMapping("/event/tickets/get")
	Set<Seat> getTickets(@RequestBody EventId eventId, String login) {
		return ticketServiceUser.getTickets(eventId, login);
	}
	
	@DeleteMapping("/event/ticket/discard")
	Set<Seat> discardTickets(@RequestBody EventId eventId, Set<Seat> seats, String login) {
		return ticketServiceUser.discardTickets(eventId, seats, login);
	}
}
