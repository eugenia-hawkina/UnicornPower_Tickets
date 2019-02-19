package telran.ashkelon2018.ticket.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.dto.EventCancellationDto;
import telran.ashkelon2018.ticket.dto.EventListByHallDateDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;
import telran.ashkelon2018.ticket.service.TicketServiceManager;

@RestController
@RequestMapping()
public class TicketServiceManagerController {
	
	@Autowired
	TicketServiceManager ticketService;

	@PostMapping("/manager/event")
	public Event addEvent(@RequestBody NewEventDto newEventDto, Principal principal) {
		return ticketService.addEvent(newEventDto, principal);
	}
	
	@PutMapping("/manager/event")
	public Event updateEvent(@RequestBody UpdateEventDto updateEventDto, Principal principal) {
		return ticketService.updateEvent(updateEventDto, principal);
	}
	
	@PutMapping("/event/info")
	public Event receiveEventInfo(@RequestBody EventId eventId) {
		return ticketService.receiveEventInfo(eventId);
	}

	@GetMapping("/manager/events")
	public Set<Event> receiveUserUpcomingEvents(Principal principal){
		return ticketService.receiveUserUpcomingEvents(principal);
	}	
	
	@PostMapping("/events/search")
	public Set<Event> receiveEventList(@RequestBody EventListByHallDateDto filter, @RequestParam int page, @RequestParam int size){
		return ticketService.receiveEventList(filter, page, size);
	}
	
	@PutMapping("/manager/event/cancellation")
	public Event cancelEvent(@RequestBody EventCancellationDto eventCancellation, Principal principal) {
		return ticketService.cancelEvent(eventCancellation, principal);
	}
}
