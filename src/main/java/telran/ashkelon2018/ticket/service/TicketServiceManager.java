package telran.ashkelon2018.ticket.service;

import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.dto.EventCancellationDto;
import telran.ashkelon2018.ticket.dto.EventListByDateDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;

public interface TicketServiceManager {
	
	Event addEvent(NewEventDto newEventDto);
	
	Event updateEvent(UpdateEventDto updateEventDto);
	
	Event receiveEventInfo(EventId eventId);

	Set<Event> receiveUserUpcomingEvents();	// managerId from token
	
	Set<Event> receiveEventList(EventListByDateDto filter, int page, int size);
	
	Event cancelEvent(EventCancellationDto eventCancellation);

}
