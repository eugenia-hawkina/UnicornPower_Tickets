package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.dto.EventCancellationDto;
import telran.ashkelon2018.ticket.dto.EventListByHallDateDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;

public interface TicketServiceManager {
	
	Event addEvent(NewEventDto newEventDto, Principal principal);
	
	Event updateEvent(UpdateEventDto updateEventDto, Principal principal);
	
	Event receiveEventInfo(EventId eventId);

	Set<Event> receiveUserUpcomingEvents(Principal principal);	// managerId from token
	
	Set<Event> receiveEventList(EventListByHallDateDto filter, int page, int size);
	
	Event cancelEvent(EventCancellationDto eventCancellation, Principal principal);

}
