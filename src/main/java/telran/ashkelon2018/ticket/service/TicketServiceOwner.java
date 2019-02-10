package telran.ashkelon2018.ticket.service;

import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileDtoForOwner;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;

public interface TicketServiceOwner {
	
	//FIXME method for return ALL users	
	AccountProfileDtoForOwner findUser(String login);
	
	ManagerAccountProfileDto addHallToManager(String login, String hallId);
	
	AccountProfileDtoForOwner removeManagerRole(String login);
	
	Set<Event> receiveHiddenEvents();
	
	EventApprovedDto approveEvent(EventId eventId);
	
	Seat printTicket(SeatId seatId, String login);
	
	Seat discardTicket(SeatId seatId, String login);

}
