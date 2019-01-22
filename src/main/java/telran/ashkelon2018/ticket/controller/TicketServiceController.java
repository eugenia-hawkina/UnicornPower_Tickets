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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;
import telran.ashkelon2018.ticket.service.TicketService;

@RestController
@RequestMapping("/manager")
public class TicketServiceController {
	
	@Autowired
	TicketService ticketService;

	@PostMapping("/event")
	public Event addEvent(@RequestBody NewEventDto newEventDto) {
		return ticketService.addEvent(newEventDto);
	}
	
	@PutMapping("/event/{eventId}")
	public Event updateEvent(@PathVariable EventId eventId, @RequestBody UpdateEventDto updateEventDto) {
		return ticketService.updateEvent(eventId, updateEventDto);
	}
	
	@GetMapping("/event/{eventId}")
	public Event receiveEventInfo(@PathVariable EventId eventId) {
		return ticketService.receiveEventInfo(eventId);
	}

	// FIXME // managerId from token
	@GetMapping("/{managerId}/events")
	public Set<Event> receiveManagerUpcomingEvents(){
		return ticketService.receiveManagerUpcomingEvents();
	}	
	
	@GetMapping("/events/search")
	public Set<Event> receiveEventList(@RequestBody EventListByDateDto filter, @RequestParam int page, @RequestParam int size){
		return ticketService.receiveEventList(filter);
	}
	
	@DeleteMapping("/event/{eventId}")
	public Event cancelEvent(@PathVariable EventId eventId, @RequestBody String reason) {
		return ticketService.cancelEvent(eventId, reason);
	}
}
