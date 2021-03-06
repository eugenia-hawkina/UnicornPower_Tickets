package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.NewHallDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileForOwnerDto;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;

public interface TicketServiceOwner {
	
	
	AccountProfileForOwnerDto findUser(String login, Principal principal);
	
	Set<AccountProfileForOwnerDto> findAllUsers(int page, int size, Principal principal);
	
	Set<AccountProfileForOwnerDto> findAllManagers(int page, int size, Principal principal);
	
	Set<Event> findManagerUpcomingEvents(int page, int size, String login, Principal principal);
	
	ManagerAccountProfileDto addHallToManager(String login, String hallId, Principal principal);
	
	ManagerAccountProfileDto removeHallFromManager(String login, String hallId, Principal principal);
	
	AccountProfileForOwnerDto addManagerRole(String login, Principal principal);
	
	AccountProfileForOwnerDto removeManagerRole(String login, Principal principal);
	
	Set<Event> receiveHiddenEvents(Principal principal);
	
	Set<Event> receiveActiveAndHiddenEventsByHall(int page, int size, String hallId, Principal principal);
	
	EventApprovedDto approveEvent(EventId eventId, Principal principal);
	
	boolean addHall(NewHallDto newHallDto, Principal principal);

	NewHallDto changeHallMaxCapacity(String hallId, Integer maxCapacity, Principal principal);
	
}
