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
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.NewHallDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileForOwnerDto;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;
import telran.ashkelon2018.ticket.service.TicketServiceOwner;

// FIXME @CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/owner")
public class TicketServiceOwnerController {

	@Autowired
	TicketServiceOwner ticketServiceOwner;
	
	@GetMapping("/{login}")
	public AccountProfileForOwnerDto findUser(@PathVariable String login, Principal principal) {
		return ticketServiceOwner.findUser(login, principal);
	}
	
	@PutMapping("/{login}/hall/{hallId}")
	public ManagerAccountProfileDto addHallToManager(@PathVariable String login, 
			@PathVariable String hallId, Principal principal) {
		return ticketServiceOwner.addHallToManager(login, hallId, principal);
	}
	
	@DeleteMapping("/roles/{login}")
	public AccountProfileForOwnerDto removeManagerRole(@PathVariable String login, Principal principal) {
		return ticketServiceOwner.removeManagerRole(login, principal);
	}
	
	@GetMapping("/events/hidden")
	public Set<Event> receiveHiddenEvents(Principal principal){
		return ticketServiceOwner.receiveHiddenEvents(principal);
	}
	
	@PutMapping("/event/approve")
	public EventApprovedDto approveEvent(@RequestBody EventId eventId, Principal principal) {
		return ticketServiceOwner.approveEvent(eventId, principal);
	}
	
	
	@GetMapping("/event/ticket/print")
	Seat printTicket(SeatId seatId, String login, Principal principal) {
		return ticketServiceOwner.printTicket(seatId, login, principal);
	}
	
	@DeleteMapping("/event/ticket/discard")
	Seat discardTicket(SeatId seatId, String login, Principal principal) {
		return ticketServiceOwner.discardTicket(seatId, login, principal);
	}
	
	@GetMapping("/users")
	Set<AccountProfileForOwnerDto> findAllUsers(@RequestParam int page, 
			@RequestParam int size, Principal principal){
		return ticketServiceOwner.findAllUsers(page, size, principal);
	}
	
	@PostMapping("/hall/new")
	boolean addHall(@RequestBody NewHallDto newHallDto, Principal principal) {
		return ticketServiceOwner.addHall(newHallDto, principal);
	}

	@PutMapping("/hall/{hallId}/capacity/{maxCapacity}")
	NewHallDto changeMaxCapacityToHall(@PathVariable String hallId, 
			@PathVariable Integer maxCapacity, Principal principal) {
		return ticketServiceOwner.changeMaxCapacityToHall(hallId, maxCapacity, principal);
	}
}
