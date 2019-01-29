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
@Document(collection="ticketService_ArchivedEvents")
public class EventArchived {
	EventStatus eventStatus;
	String eventName;
	String artist;
	@Id 
	EventId eventId;
	Integer eventDurationMinutes;	
	Set<Seat> seats;
	EventType eventType; 
	String description;
	Set<String> images;
	String cancellationReason;
	String userId;
	
	
	public EventArchived(Event event) {
		this.eventStatus = event.getEventStatus();
		this.eventName = event.getEventName();
		this.artist = event.getArtist();
		this.eventId = event.getEventId();
		this.eventDurationMinutes = event.getEventDurationMinutes();
		this.seats = event.getSeats();
		this.eventType = event.getEventType();
		this.description = event.getDescription();
		this.images = event.getImages();
		this.cancellationReason = event.getCancellationReason();
		this.userId = event.getUserId();
}
}
