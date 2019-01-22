package telran.ashkelon2018.ticket.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = { "eventId" })
@Document(collection="ticketService_UpcomingEvents")
public class Event {
	EventStatus eventStatus;
	String eventName;
	String artist;
	// @EmbeddedId - for sql
	@Id EventId eventId;
	Integer eventDurationMinutes;	
	Set<Seat> seats;
	EventType eventType; 
	String description;
	Set<String> images;
	String cancellationReason;
}
