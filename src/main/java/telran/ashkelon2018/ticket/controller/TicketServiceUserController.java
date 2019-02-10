package telran.ashkelon2018.ticket.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.dto.EventListByHallDateDto;
import telran.ashkelon2018.ticket.service.TicketServiceUser;

@RestController
@RequestMapping("/user")
public class TicketServiceUserController {
	
	@Autowired
	TicketServiceUser ticketServiceUser;

	
	@GetMapping("/events")
	public Set<Event> receiveUpcomingEvents(int page, int size){
		return ticketServiceUser.receiveUpcomingEvents(page, size);
	}
	
	@GetMapping("/events/archive")
	public Set<EventArchived> receiveArchivedEvents(int page, int size){
		return ticketServiceUser.receiveArchivedEvents(page, size);
	}
	
	@PostMapping("/events/dates")
	public Set<Event> receiveEventsByDate(@RequestBody EventListByHallDateDto filter,
			int page, int size){
		return ticketServiceUser.receiveEventsByDate(filter, page, size);
	}
	
	@GetMapping("/events/hall/{hallId}")
	public Set<Event> receiveEventsByHall(@PathVariable String hallId, int page, int size){
		return ticketServiceUser.receiveEventsByHall(hallId, page, size);
	}
	
	@GetMapping("/events/artist/{artist}")
	public Set<Event> receiveEventsByArtist(@PathVariable String artist, int page, int size){
		return ticketServiceUser.receiveEventsByArtist(artist, page, size);
	}
	
	@PutMapping("event/ticket/purchase")
	public Seat buyTicket(@RequestBody EventId eventId, SeatId seatId, String login) {
		return ticketServiceUser.buyTicket(eventId, seatId, login);
	}
	
	
	// for registered only!!!
	@GetMapping("/events/visited")
	Set<Event> receiveVisitedEvents(String login, int page, int size){
		return ticketServiceUser.receiveVisitedEvents(login, page, size);
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