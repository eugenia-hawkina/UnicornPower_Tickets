package telran.ashkelon2018.ticket.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.dao.EventArchivedRepository;
import telran.ashkelon2018.ticket.dao.EventCancelledRepository;
import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileForOwnerDto;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;
import telran.ashkelon2018.ticket.enums.UserRole;
import telran.ashkelon2018.ticket.exceptions.NotFoundException;
import telran.ashkelon2018.ticket.exceptions.UserHasNotRightsException;


@Service
public class TicketServiceOwnerImpl implements TicketServiceOwner {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventCancelledRepository cancelledEventRepository;
	
	@Autowired
	EventArchivedRepository archivedEventRepository;

	@Override
	public AccountProfileForOwnerDto findUser(String login) {
		if (!userAccountRepository.existsById(login)) {
			throw new NotFoundException();
		}
		UserAccount userAccount = userAccountRepository.findById(login).get();
		return convertToAccountProfileForOwnerDto(userAccount);
	}
		
	private AccountProfileForOwnerDto convertToAccountProfileForOwnerDto(UserAccount account) {
		return AccountProfileForOwnerDto.builder().login(account.getLogin()).name(account.getName())
				.phone(account.getPhone()).roles(account.getRoles()).visitedEvents(account.getVisitedEvents())
				.halls(account.getHalls()).build();
	}

	@Override
	public ManagerAccountProfileDto addHallToManager(String login, String hallId) {
		if (!userAccountRepository.existsById(login)) {
			throw new NotFoundException();
		}
		UserAccount userAccount = userAccountRepository.findById(login).get();
		if (!userAccount.getRoles().contains(UserRole.MANAGER)) {
			throw new UserHasNotRightsException("User not a MANAGER");
		}
		userAccount.addHall(hallId);
		userAccountRepository.save(userAccount);
		return convertToManagerAccountProfileDto(userAccount);
	}
	
	private ManagerAccountProfileDto convertToManagerAccountProfileDto(UserAccount manager) {
		return ManagerAccountProfileDto.builder().login(manager.getLogin()).name(manager.getName())
				.phone(manager.getPhone()).halls(manager.getHalls()).build();
	}

	@Override
	public AccountProfileForOwnerDto removeManagerRole(String login) {
		if (!userAccountRepository.existsById(login)) {
			throw new NotFoundException();
		}
		UserAccount userAccount = userAccountRepository.findById(login).get();
		if (!userAccount.getRoles().contains(UserRole.MANAGER)) {
			throw new UserHasNotRightsException("User not a MANAGER");
		}
		userAccount.removeRole(UserRole.MANAGER);
		userAccountRepository.save(userAccount);
		return convertToAccountProfileForOwnerDto(userAccount);
	}

	@Override
	public Set<Event> receiveHiddenEvents() {
		// FIXME without pagination
		Set<Event> hiddenEvents = new HashSet<>();
		hiddenEvents.addAll(eventRepository.findAllBy()
			.collect(Collectors.toSet()));
		return hiddenEvents;
	}

	public Set<Event> receiveHiddenEvents(int page, int size) {
		// FIXME with pagination
		Set<Event> hiddenEvents = new HashSet<>();
		hiddenEvents.addAll(eventRepository.findAllBy()
			.skip(size*(page-1))
			.limit(size)
			.collect(Collectors.toSet()));
		return hiddenEvents;
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
