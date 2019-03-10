package telran.ashkelon2018.ticket.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.NewHallDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileForOwnerDto;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;
import telran.ashkelon2018.ticket.service.TicketServiceOwner;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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

	@DeleteMapping("/{login}/hall/{hallId}")
	public ManagerAccountProfileDto removeHallFromManager(@PathVariable String login, 
			@PathVariable String hallId, Principal principal) {
		return ticketServiceOwner.removeHallFromManager(login, hallId, principal);
	}
	@PutMapping("/roles/{login}")
	public AccountProfileForOwnerDto addManagerRole(@PathVariable String login, Principal principal) {
		return ticketServiceOwner.addManagerRole(login, principal);
	}
	
	@DeleteMapping("/roles/{login}")
	public AccountProfileForOwnerDto removeManagerRole(@PathVariable String login, Principal principal) {
		return ticketServiceOwner.removeManagerRole(login, principal);
	}
	
	@GetMapping("/events/hidden")
	public Set<Event> receiveHiddenEvents(Principal principal){
		return ticketServiceOwner.receiveHiddenEvents(principal);
	}
	
	@GetMapping("/events/hall/{hallId}")
	public Set<Event> receiveActiveAndHiddenEventsByHall(@RequestParam int page, 
			@RequestParam int size, @PathVariable String hallId, Principal principal){
		return ticketServiceOwner.receiveActiveAndHiddenEventsByHall(page, size, hallId, principal);
	}
	
	@PutMapping("/event/approve")
	public EventApprovedDto approveEvent(@RequestBody EventId eventId, Principal principal) {
		return ticketServiceOwner.approveEvent(eventId, principal);
	}
	
	@GetMapping("/users")
	Set<AccountProfileForOwnerDto> findAllUsers(@RequestParam int page, 
			@RequestParam int size, Principal principal){
		return ticketServiceOwner.findAllUsers(page, size, principal);
	}
	
	@GetMapping("/managers")
	Set<AccountProfileForOwnerDto> findAllManagers(@RequestParam int page, 
			@RequestParam int size, Principal principal){
		return ticketServiceOwner.findAllManagers(page, size, principal);
	}
	
	@GetMapping("/{login}/events")
	Set<Event> findManagerUpcomingEvents(@RequestParam int page, @RequestParam int size, 
			@PathVariable String login, Principal principal){
		return ticketServiceOwner.findManagerUpcomingEvents(page, size, login, principal);
	}
	
	@PostMapping("/hall/new")
	boolean addHall(@RequestBody NewHallDto newHallDto, Principal principal) {
		return ticketServiceOwner.addHall(newHallDto, principal);
	}

	@PutMapping("/hall/{hallId}/capacity/{maxCapacity}")
	NewHallDto changeMaxHallCapacity(@PathVariable String hallId, 
			@PathVariable Integer maxCapacity, Principal principal) {
		return ticketServiceOwner.changeHallMaxCapacity(hallId, maxCapacity, principal);
	}
}
