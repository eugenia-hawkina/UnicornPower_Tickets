package telran.ashkelon2018.ticket.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import telran.ashkelon2018.ticket.domain.Seat;
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
	@JsonFormat(pattern = "yyyy-MM-dd 'T' HH:mm") LocalDateTime eventStart; 
	Integer eventDurationMinutes;	
	String hallId;
	@Singular Set<SeatDto> seats;
	EventType eventType; 
	String description;
	Set<String> images;

}
