package telran.ashkelon2018.ticket.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.dao.ArchivedEventRepository;
import telran.ashkelon2018.ticket.dao.CancelledEventRepository;
import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileDtoForOwner;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;

@Service
public class TicketServiceOwnerImpl implements TicketServiceOwner {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	CancelledEventRepository cancelledEventRepository;
	
	@Autowired
	ArchivedEventRepository archivedEventRepository;

	@Override
	public AccountProfileDtoForOwner findUser(String login) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ManagerAccountProfileDto convertToManagerAccountProfileDto(UserAccount manager) {
		return ManagerAccountProfileDto.builder().login(manager.getLogin()).name(manager.getName())
				.phone(manager.getPhone()).halls(manager.getHalls()).build();
	}


	@Override
	public ManagerAccountProfileDto addHallToManager(String login, String hallId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountProfileDtoForOwner removeManagerRole(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Event> receiveHiddenEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventApprovedDto approveEvent(EventId eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seat printTicket(SeatId seatId, String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seat discardTicket(SeatId seatId, String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
