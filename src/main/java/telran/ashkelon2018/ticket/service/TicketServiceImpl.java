package telran.ashkelon2018.ticket.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.dao.ArchivedEventRepository;
import telran.ashkelon2018.ticket.dao.CancelledEventRepository;
import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventCancelled;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.dto.EventCancellationDto;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.SeatDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;
import telran.ashkelon2018.ticket.exceptions.BadRequestException;
import telran.ashkelon2018.ticket.exceptions.EventExistsException;
import telran.ashkelon2018.ticket.exceptions.NotFoundException;

@Service
public class TicketServiceImpl implements TicketService {
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	CancelledEventRepository cancelledEventRepository;
	
	@Autowired
	ArchivedEventRepository archivedEventRepository;

	
	@Override
	public Event addEvent(NewEventDto newEventDto) {
		Event eventCheck = eventRepository.findById(newEventDto.getEventId()).orElse(null);
		if( eventCheck != null) {
			throw new EventExistsException("Sorry, event already exists");
		}
		try { 
			// FIXME - get id from token
			String userId = "get from token";
			Set<Seat> seats = convertSeatDtosToSeats(newEventDto.getSeatDto());		
			Event event = new Event(newEventDto.getEventName(), 
					newEventDto.getArtist(), newEventDto.getEventId(), newEventDto.getEventDurationMinutes(),
					seats, newEventDto.getEventType(), newEventDto.getDescription(), 
					newEventDto.getImages(), userId);
			eventRepository.save(event);		
			return event;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Huston, we have a problem - Bad Request");
		}
	}

	@Override
	public Event updateEvent(UpdateEventDto updateEventDto) {
		// FIXME - auth
		Event event = eventRepository.findById(updateEventDto.getEventId()).orElse(null);
		if(event == null) {
			throw new NotFoundException("Nicht Aufgefunden!");
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
			throw new BadRequestException("I need more information!");
		}
		
		event.setEventName(name);
		event.setArtist(artist);
		event.setDescription(description);
		event.setEventStatus(eventStatus);
		event.setEventDurationMinutes(duration);
		event.setEventType(type);
		event.setImages(images);	
		event.setSeats(seats);
		
		eventRepository.save(event);
		return event;
	}

	private Set<Seat> convertSeatDtosToSeats (Set<SeatDto> seatDto) {
		if(seatDto == null) {
			throw new RuntimeException("Alarm, no seats");
		}
		return seatDto.stream()
				.map(sd -> new Seat(sd.getSeatId(), sd.getPriceRange(), true, false))
				.collect(Collectors.toSet());	
	}

	@Override
	public Event receiveEventInfo(EventId eventId) {
		// FIXME - auth
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event == null) {
			throw new NotFoundException("Nicht Aufgefunden!");
		}
		return event;
	}

	@Override
	public Set<Event> receiveUserUpcomingEvents() {
		// TODO Auto-generated method stub - from token
		return null;
	}

	@Override
	public Set<Event> receiveEventList(EventListByDateDto filter, int page, int size) {
		Set<Event> eventsAll = new HashSet<>();
		LocalDate dateFrom = filter.getDateFrom();
		LocalDate dateTo = filter.getDateTo();
		String hallId = filter.getHallId();		
		eventsAll.addAll(eventRepository.findByEventIdHallIdAndEventIdEventStartBetween(hallId, dateFrom, dateTo)
				.skip(size*(page-1))
				.limit(size)
				.collect(Collectors.toSet()));		
		return eventsAll;
	}

	@Override
	public Event cancelEvent(EventCancellationDto eventCancellation) {
		// FIXME - auth
		EventId id = eventCancellation.getEventId();
		Event event = eventRepository.findById(id).orElse(null);
		if(event == null) {
			throw new NotFoundException("Nicht Aufgefunden!");
		}
		event.setCancellationReason(eventCancellation.getReason());
		event.setEventStatus(EventStatus.CANCELLED);
		EventCancelled cancelledEvent = new EventCancelled(event);
		cancelledEventRepository.save(cancelledEvent);		
		eventRepository.deleteById(id);
		return event;
	}

}