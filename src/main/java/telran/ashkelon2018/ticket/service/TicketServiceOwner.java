package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Stream;

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
	
	Set<AccountProfileForOwnerDto> findAllUsers(int page, int size);
	
	ManagerAccountProfileDto addHallToManager(String login, String hallId);
	
	AccountProfileForOwnerDto removeManagerRole(String login);
	
	Set<Event> receiveHiddenEvents();
	
	EventApprovedDto approveEvent(EventId eventId);
	
	Seat printTicket(SeatId seatId, String login);
	
	Seat discardTicket(SeatId seatId, String login);
	
	boolean addHall(NewHallDto newHallDto);

	NewHallDto changeMaxCapacityToHall(String hallId, Integer maxCapacity);
}
