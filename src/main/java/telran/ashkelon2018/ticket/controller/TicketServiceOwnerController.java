package telran.ashkelon2018.ticket.controller;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Stream;

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
			@PathVariable String hallId) {
		return ticketServiceOwner.addHallToManager(login, hallId);
	}
	
	@DeleteMapping("/roles/{login}")
	public AccountProfileForOwnerDto removeManagerRole(@PathVariable String login) {
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
	
	@GetMapping("/users")
	Set<AccountProfileForOwnerDto> findAllUsers(@RequestParam int page, @RequestParam int size){
		return ticketServiceOwner.findAllUsers(page, size);
	}
	
	@PostMapping("/hall/new")
	boolean addHall(@RequestBody NewHallDto newHallDto) {
		return ticketServiceOwner.addHall(newHallDto);
	}

	@PutMapping("/hall/{hallId}/capacity/{maxCapacity}")
	NewHallDto changeMaxCapacityToHall(@PathVariable String hallId, @PathVariable Integer maxCapacity) {
		return ticketServiceOwner.changeMaxCapacityToHall(hallId, maxCapacity);
	}
}
