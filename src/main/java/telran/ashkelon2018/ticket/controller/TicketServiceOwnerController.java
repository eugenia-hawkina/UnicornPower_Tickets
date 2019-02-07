package telran.ashkelon2018.ticket.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileDtoForOwner;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;
import telran.ashkelon2018.ticket.service.TicketServiceOwner;

// FIXME @CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/owner")
public class TicketServiceOwnerController {

	@Autowired
	TicketServiceOwner ticketServiceOwner;
	
	@GetMapping("/{login}")
	public AccountProfileDtoForOwner findUser(@PathVariable String login) {
		return ticketServiceOwner.findUser(login);
	}
	
	@PutMapping("/{login}/hall/{hallId}")
	// FIXME does put work with pathVarianle? or get only
	public ManagerAccountProfileDto addHallToManager(@PathVariable String login, 
			@PathVariable String hallId) {
		return ticketServiceOwner.addHallToManager(login, hallId);
	}
	
	@DeleteMapping("/roles/{login}")
	public AccountProfileDtoForOwner removeManagerRole(@PathVariable String login) {
		return ticketServiceOwner.removeManagerRole(login);
	}
	
	@GetMapping("/events/hidden")
	public Set<Event> receiveHiddenEvents(){
		return ticketServiceOwner.receiveHiddenEvents();
	}
	
	@PutMapping("/event/approve")
	public EventApprovedDto approveEvent(EventId eventId) {
		return ticketServiceOwner.approveEvent(eventId);
	}
	
	
	@GetMapping("/event/ticket/print")
	Seat printTicket(SeatId seatId, String login) {
		return ticketServiceOwner.printTicket(seatId, login);
	}
	
	@DeleteMapping("/event/ticket/discard")
	Seat discardTicket(SeatId seatId, String login) {
		return ticketServiceOwner.discardTicket(seatId, login);
	}
}
