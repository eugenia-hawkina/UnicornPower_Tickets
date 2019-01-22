package telran.ashkelon2018.ticket.service;

import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;

public interface TicketService {
	
	Event addEvent(NewEventDto newEventDto);
	
	Event updateEvent(EventId eventId, UpdateEventDto updateEventDto);
	
	Event receiveEventInfo(EventId eventId);

	Set<Event> receiveManagerUpcomingEvents();	// managerId from token
	
	Set<Event> receiveEventList(EventListByDateDto filter);
	
	Event cancelEvent(EventId eventId, String reason);
	

}
