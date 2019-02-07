package telran.ashkelon2018.ticket.controller;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.service.TicketServiceUser;

@RestController
@RequestMapping("/user")
public class TicketServiceUserController {
	
	@Autowired
	TicketServiceUser ticketServiceUser;

	
	@GetMapping("/events")
	public Set<Event> recieveUpcomingEvents(){
		return ticketServiceUser.recieveUpcomingEvents();
	}
	
	@GetMapping("/events/archive")
	public Set<Event> receiveArchivedEvents(){
		return ticketServiceUser.receiveArchivedEvents();
	}
	
	@GetMapping("/events/dates")
	public Set<Event> receiveEventsByDate(@RequestBody LocalDate from, LocalDate to){
		return ticketServiceUser.receiveEventsByDate(from, to);
	}
	
	@GetMapping("/events/hall/{hallId}")
	public Set<Event> receiveEventsByHall(@PathVariable String hallId){
		return ticketServiceUser.receiveEventsByHall(hallId);
	}
	
	@GetMapping("/events/artist/{artist}")
	public Set<Event> receiveEventsByArtist(@PathVariable String artist){
		return ticketServiceUser.receiveEventsByArtist(artist);
	}
	
	@PutMapping("event/ticket/purchase")
	public Seat buyTicket(@RequestBody EventId eventId, SeatId seatId, String login) {
		return ticketServiceUser.buyTicket(eventId, seatId, login);
	}
	
	
	// for registered only!!!
	@GetMapping("/events/visited")
	Set<Event> receiveVisitedEvents(String login){
		return ticketServiceUser.receiveVisitedEvents(login);
	}
	
	@GetMapping("/event/ticket/print")
	Seat printTicket(SeatId seatId, String login) {
		return ticketServiceUser.printTicket(seatId, login);
	}
	
	@DeleteMapping("/event/ticket/discard")
	Seat discardTicket(SeatId seatId, String login) {
		return ticketServiceUser.discardTicket(seatId, login);
	}
}
