package telran.ashkelon2018.ticket.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.enums.EventType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
//	EventStatus eventStatus; 
	String eventName;
	String artist;	
	Integer eventDurationMinutes;	
	EventId eventId;
	Set<SeatDto> seatDto;
	EventType eventType; 
	String description;
	Set<String> images;

}
