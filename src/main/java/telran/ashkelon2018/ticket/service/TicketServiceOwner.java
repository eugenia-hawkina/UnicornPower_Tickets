package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.NewHallDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileForOwnerDto;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;

public interface TicketServiceOwner {
	
	
	AccountProfileForOwnerDto findUser(String login, Principal principal);
	
	Set<AccountProfileForOwnerDto> findAllUsers(int page, int size, Principal principal);
	
	ManagerAccountProfileDto addHallToManager(String login, String hallId, Principal principal);
	
	AccountProfileForOwnerDto addManagerRole(String login, Principal principal);
	
	AccountProfileForOwnerDto removeManagerRole(String login, Principal principal);
	
	Set<Event> receiveHiddenEvents(Principal principal);
	
	EventApprovedDto approveEvent(EventId eventId, Principal principal);
	
	Seat printTicket(SeatId seatId, String login, Principal principal);
	
	Seat discardTicket(SeatId seatId, String login, Principal principal);
	
	boolean addHall(NewHallDto newHallDto, Principal principal);

	NewHallDto changeMaxCapacityToHall(String hallId, Integer maxCapacity, Principal principal);
}
