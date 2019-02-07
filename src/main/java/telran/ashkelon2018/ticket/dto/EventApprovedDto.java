package telran.ashkelon2018.ticket.dto;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventApprovedDto {
	EventStatus eventStatus;
	String eventName;
	String artist;
	EventId eventId;
	Integer eventDurationMinutes;	
	EventType eventType; 
	String description;
	String userId;
}
