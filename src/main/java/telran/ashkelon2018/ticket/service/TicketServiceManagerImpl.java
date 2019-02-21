package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.time.LocalDate;
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
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventCancelled;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.dto.EventCancellationDto;
import telran.ashkelon2018.ticket.dto.EventListByHallDateDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.SeatDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;
import telran.ashkelon2018.ticket.enums.UserRole;
import telran.ashkelon2018.ticket.exceptions.AccessDeniedException;
import telran.ashkelon2018.ticket.exceptions.BadRequestException;
import telran.ashkelon2018.ticket.exceptions.EventExistsException;
import telran.ashkelon2018.ticket.exceptions.NotFoundException;

@Service
public class TicketServiceManagerImpl implements TicketServiceManager {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventCancelledRepository cancelledEventRepository;
	
	@Autowired
	EventArchivedRepository archivedEventRepository;
	
	@Autowired
	UserAccountRepository userAccountRepository;

	
	@Override 
	public Event addEvent(NewEventDto newEventDto, Principal principal) {
		String id = principal.getName();
		UserAccount manager = userAccountRepository.findById(id).orElse(null);
		if(!manager.getRoles().contains(UserRole.MANAGER)) {
			throw new AccessDeniedException("Access denied, you are not a manager");
		}
		EventId eventId = newEventDto.getEventId();
		Event eventCheck = eventRepository.findById(eventId).orElse(null);
		if(eventCheck != null) {
			throw new EventExistsException("Sorry, event already exists");
		}
		String name = newEventDto.getEventName(); 
		String artist = newEventDto.getArtist();		 
		int duration = newEventDto.getEventDurationMinutes();
		Set <Seat> seats = convertSeatDtosToSeats(newEventDto.getSeatDto());
		EventType eventType = newEventDto.getEventType();
		String description = newEventDto.getDescription(); 
		Set<String> images = newEventDto.getImages();
		if(name == null || artist == null || duration == 0 || images == null) {
			throw new BadRequestException("More information needed");
		}				
		Event event = new Event(name, artist, eventId, duration, seats, eventType, 
				description, images, id);
		eventRepository.save(event);		
		return event;		
	}

	@Override
	public Event updateEvent(UpdateEventDto updateEventDto, Principal principal) {
		String id = principal.getName();
		UserAccount manager = userAccountRepository.findById(id).orElse(null);
		if(!manager.getRoles().contains(UserRole.MANAGER)) {
			throw new AccessDeniedException("Access denied, you are not a manager");
		}
		Event event = eventRepository.findById(updateEventDto.getEventId()).orElse(null);
		if(event == null) {
			throw new NotFoundException("Event not found");
		}
		String name = updateEventDto.getEventName();
		String artist = updateEventDto.getArtist();
		String description = updateEventDto.getDescription();
		EventStatus eventStatus = updateEventDto.getEventStatus();
		Integer duration = updateEventDto.getEventDurationMinutes();
		EventType type = updateEventDto.getEventType();
		Set<String> images = updateEventDto.getImages();
		Set<Seat> seats = convertSeatDtosToSeats(updateEventDto.getSeatDto());
		
		if(name==null || artist==null || description==null || eventStatus==null || duration==null 
				|| type==null || images==null || seats==null) {
			throw new BadRequestException("More information needed");
		}
		event.setEventName(name);
		event.setArtist(artist);
		event.setDescription(description);
		if ((event.getEventStatus().equals(EventStatus.ACTIVE) 
						|| event.getEventStatus().equals(EventStatus.CANCELLED))
				&& (eventStatus.equals(EventStatus.ACTIVE) 
						|| eventStatus.equals(EventStatus.CANCELLED))) {
			event.setEventStatus(eventStatus);
		}		
		event.setEventDurationMinutes(duration);
		event.setEventType(type);
		event.setImages(images);	
		event.setSeats(seats);		
		eventRepository.save(event);
		return event;
	}

	private Set <Seat> convertSeatDtosToSeats (Set<SeatDto> seatDto) {
		if(seatDto == null) {
			throw new RuntimeException("Alarm, no seats");
		}
		return seatDto.stream()	
				.map(sd -> new Seat(sd.getSeatId(), sd.getPriceRange(), true, false, "free"))
				.collect(Collectors.toSet());	
	}

	@Override
	public Event receiveEventInfo(EventId eventId) {
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event == null) {
			throw new NotFoundException("Event not found");
		}
		return event;
	}
 
	@Override
	public Set<Event> receiveUserUpcomingEvents(Principal principal) {
		String id = principal.getName();
		Set<Event> events = eventRepository.findByUserId(id);
		return events;
	}

	@Override
	public Set<Event> receiveEventList(EventListByHallDateDto filter, int page, int size) {
		Set<Event> eventsAll = new HashSet<>();
		Set<EventArchived> eventsArchivedAll = new HashSet<>();
		LocalDate dateFrom = filter.getDateFrom();
		LocalDate dateTo = filter.getDateTo();
		if(dateFrom == null || dateTo == null) {
			throw new BadRequestException("I need two dates");
		}
		if(dateTo.isBefore(dateFrom)) {
			LocalDate tmp = dateFrom;
			dateFrom = dateTo;
			dateTo = tmp;
		}
		String hallId = filter.getHallId();
		if(dateFrom.isBefore(LocalDate.now()) && dateTo.isBefore(LocalDate.now())) {			
			eventsArchivedAll.addAll(archivedEventRepository
					.findByEventIdHallIdAndEventIdEventStartBetween(hallId, dateFrom, dateTo)
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
			eventsAll = eventsArchivedAll.stream()
					.map(e -> convertArchivedEventToEvent(e))
					.collect(Collectors.toSet());
		}
		if (dateFrom.isAfter(LocalDate.now()) && dateTo.isAfter(LocalDate.now())) {			
			eventsAll.addAll(eventRepository
					.findByEventIdHallIdAndEventIdEventStartBetween(hallId, dateFrom, dateTo)
					.skip(size * (page - 1))
					.limit(size)
					.collect(Collectors.toSet()));
		}
		// FIXME combine info from two repositories
		if(dateFrom.isBefore(LocalDate.now()) && dateTo.isAfter(LocalDate.now())) {
			throw new BadRequestException("Both dates should be either beforeNow or afterNow");
		}
		return eventsAll;
	}

	private Event convertArchivedEventToEvent(EventArchived e) {
		return 	Event.builder()
					.eventStatus(e.getEventStatus())
					.eventName(e.getEventName())
					.artist(e.getArtist())
					.eventId(e.getEventId())
					.eventDurationMinutes(e.getEventDurationMinutes())
					.seats(e.getSeats())
					.eventType(e.getEventType())
					.description(e.getDescription())
					.images(e.getImages())
					.cancellationReason(e.getCancellationReason())
					.userId(e.getUserId())
					.build();
	}

	@Override
	public Event cancelEvent(EventCancellationDto eventCancellation, Principal principal) {
		String managerId = principal.getName();
		UserAccount manager = userAccountRepository.findById(managerId).orElse(null);
		if(!manager.getRoles().contains(UserRole.MANAGER)) {
			throw new AccessDeniedException("Access denied, you are not a manager");
		}
		EventId eventId = eventCancellation.getEventId();
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event == null) {
			throw new NotFoundException("Event not found");
		}
		if(!event.getUserId().equals(managerId)) {
			System.out.println("not your event");
			throw new AccessDeniedException("Access denied, that is not your event");
		}
		String cancellationReason = eventCancellation.getReason();
		if(cancellationReason == null || cancellationReason == "") {
			throw new BadRequestException("Cancellation reason needed");
		}
		event.setCancellationReason(cancellationReason);
		event.setEventStatus(EventStatus.CANCELLED);
		EventCancelled cancelledEvent = new EventCancelled(event);
		cancelledEventRepository.save(cancelledEvent);	
		eventRepository.deleteById(eventId);
		return event;
	}

}
