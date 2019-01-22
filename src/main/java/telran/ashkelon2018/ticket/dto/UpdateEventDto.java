package telran.ashkelon2018.ticket.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import telran.ashkelon2018.ticket.domain.Seat;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventDto {
	EventStatus eventStatus;
	String eventName;
	String artist;
	Integer eventDurationMinutes;	
	@Singular Set<Seat> seats;
	EventType eventType; 
	String description;
	Set<String> images;
}
