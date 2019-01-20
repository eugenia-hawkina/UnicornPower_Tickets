package telran.ashkelon2018.ticket.dto;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;

public class NewEventDto {
//	EventStatus eventStatus; 
	String eventName;
	String artist;	
	LocalDateTime eventStart; 
	Integer eventDurationMinutes;	
	HallDto hallDto; // id = String
	EventType eventType; 
	String description;
	Set<String> images;

}
