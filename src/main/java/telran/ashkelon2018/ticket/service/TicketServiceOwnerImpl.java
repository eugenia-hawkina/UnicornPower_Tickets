package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.dao.EventArchivedRepository;
import telran.ashkelon2018.ticket.dao.EventCancelledRepository;
import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.dao.HallRepository;
import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Hall;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.SeatId;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.dto.EventApprovedDto;
import telran.ashkelon2018.ticket.dto.NewHallDto;
import telran.ashkelon2018.ticket.dto.account.AccountProfileForOwnerDto;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.UserRole;
import telran.ashkelon2018.ticket.exceptions.BadRequestException;
import telran.ashkelon2018.ticket.exceptions.HallExistsException;
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

	@Autowired
	HallRepository hallRepository;

	@Override
	public AccountProfileForOwnerDto findUser(String login, Principal principal) {
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		
		if (!userAccountRepository.existsById(login)) {
			throw new NotFoundException("No such user");
		}
		UserAccount userAccount = userAccountRepository.findById(login).get();
		return convertToAccountProfileForOwnerDto(userAccount);
	}

	private AccountProfileForOwnerDto convertToAccountProfileForOwnerDto(UserAccount account) {
		return AccountProfileForOwnerDto.builder()
				.login(account.getLogin())
				.name(account.getName())
				.phone(account.getPhone())
				.roles(account.getRoles())
				.visitedEvents(account.getVisitedEvents())
				.halls(account.getHalls())
				.build();
	}

	@Override
	public ManagerAccountProfileDto addHallToManager(String login, String hallId, Principal principal) {
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		if (!hallRepository.existsById(hallId)) {
			throw new NotFoundException("No such hall");
		}
		if (!userAccountRepository.existsById(login)) {
			throw new NotFoundException("No such user");
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
		return ManagerAccountProfileDto.builder()
				.login(manager.getLogin())
				.name(manager.getName())
				.phone(manager.getPhone())
				.halls(manager.getHalls())
				.build();
	}

	@Override
	public AccountProfileForOwnerDto removeManagerRole(String login, Principal principal) {
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		if (!userAccountRepository.existsById(login)) {
			throw new NotFoundException("No such user");
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
	public AccountProfileForOwnerDto addManagerRole(String login, Principal principal) {
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		if (!userAccountRepository.existsById(login)) {
			throw new NotFoundException("No such user");
		}
		UserAccount userAccount = userAccountRepository.findById(login).get();
		userAccount.addRole(UserRole.MANAGER);
		userAccountRepository.save(userAccount);
		return convertToAccountProfileForOwnerDto(userAccount);
	}	
	
	
	@Override
	public Set<Event> receiveHiddenEvents(Principal principal) {
		// without pagination
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		Set<Event> hiddenEvents = new HashSet<>();
		hiddenEvents.addAll(eventRepository.findByEventStatus(EventStatus.HIDDEN)
				.collect(Collectors.toSet()));
		return hiddenEvents;
	}

	public Set<Event> receiveHiddenEvents(int page, int size, Principal principal) {
		// with pagination
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		Set<Event> hiddenEvents = new HashSet<>();
		hiddenEvents.addAll(eventRepository.findByEventStatus(EventStatus.HIDDEN)
				.skip(size * (page - 1))
				.limit(size)
				.collect(Collectors.toSet()));
		return hiddenEvents;
	}

	@Override
	public EventApprovedDto approveEvent(EventId eventId, Principal principal) {
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event == null) {
			throw new BadRequestException("Wrong event id");
		}		
		event.setEventStatus(EventStatus.ACTIVE);	
		eventRepository.save(event);
		return convertEventToEventApproveDto(event);
	}

	private EventApprovedDto convertEventToEventApproveDto(Event event) {
		return EventApprovedDto.builder()
				.eventStatus(event.getEventStatus())
				.eventName(event.getEventName())
				.artist(event.getArtist())
				.eventId(event.getEventId())
				.eventDurationMinutes(event.getEventDurationMinutes())
				.eventType(event.getEventType())
				.description(event.getDescription())
				.userId(event.getUserId())
				.build();
	}

	@Override
	public Seat printTicket(SeatId seatId, String login, Principal principal) {
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		// TODO Auto-generated method stub		
		
		return null;
	}

	@Override
	public Seat discardTicket(SeatId seatId, String login, Principal principal) {
		String id = principal.getName();
		UserAccount owner = userAccountRepository.findById(id).orElse(null);
		if(!owner.getRoles().contains(UserRole.OWNER)) {
			throw new AccessDeniedException("Access denied, you are not an onwer");
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<AccountProfileForOwnerDto> findAllUsers(int page, int size, Principal principal) {
		Set<AccountProfileForOwnerDto> allUsers = new HashSet<>();
		allUsers = userAccountRepository.findAllBy()
			.skip(size * (page - 1))
			.limit(size)		
			.map(user -> convertToAccountProfileForOwnerDto(user))
			.collect(Collectors.toSet());
		return allUsers;
	}

	@Override
	public boolean addHall(NewHallDto newHallDto, Principal principal) {
		if(hallRepository.existsById(newHallDto.getHallId())) {
			throw new HallExistsException("Hall already exists");
		}
		hallRepository.save(convertNewHallDtoToHall(newHallDto));
		return true;
	}

	private Hall convertNewHallDtoToHall(NewHallDto newHallDto) {
		return new Hall(newHallDto.getHallId(), newHallDto.getLocationName(), 
				newHallDto.getHallName(), newHallDto.getCountry(), newHallDto.getRegion(), 
				newHallDto.getCity(), newHallDto.getStreet(), newHallDto.getBuiling(), 
				newHallDto.getHallType(), newHallDto.getMaxCapacity());
	}

	@Override
	public NewHallDto changeMaxCapacityToHall(String hallId, Integer maxCapacity, Principal principal) {
		if (!hallRepository.existsById(hallId)) {
			throw new NotFoundException("No such hall");
		}
		Hall hall = hallRepository.findById(hallId).orElse(null);
		hall.setMaxCapacity(maxCapacity);
		hallRepository.save(hall);
		return convertHallToNewHallDto(hall);
	}

	private NewHallDto convertHallToNewHallDto(Hall hall) {
		return NewHallDto.builder()
				.hallId(hall.getHallId())
				.locationName(hall.getLocationName())
				.hallName(hall.getHallName())
				.country(hall.getCountry())
				.region(hall.getRegion())
				.city(hall.getCity())
				.street(hall.getStreet())
				.builing(hall.getBuiling())
				.hallType(hall.getHallType())
				.maxCapacity(hall.getMaxCapacity())
				.build();
		 
	}

}
