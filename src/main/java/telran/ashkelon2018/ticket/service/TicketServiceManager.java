package telran.ashkelon2018.ticket.service;

import java.security.Principal;
import java.util.Set;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.dto.EventCancellationDto;
import telran.ashkelon2018.ticket.dto.NewEventDto;
import telran.ashkelon2018.ticket.dto.TicketGetForManagerDto;
import telran.ashkelon2018.ticket.dto.UpdateEventDto;

public interface TicketServiceManager {
	
	Event addEvent(NewEventDto newEventDto, Principal principal);
	
	Event updateEvent(UpdateEventDto updateEventDto, Principal principal);

	Set<Event> receiveMyUpcomingEvents(Principal principal);	
	
	Set<EventArchived> receiveMyArchivedEvents(Principal principal);
	
	Event cancelEvent(EventCancellationDto eventCancellation, Principal principal);
		
	Set<Seat> getTickets(TicketGetForManagerDto dto, Principal principal);

}
