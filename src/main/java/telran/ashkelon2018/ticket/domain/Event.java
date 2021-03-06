package telran.ashkelon2018.ticket.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Event {
	EventStatus eventStatus;
	String eventName;
	String artist;
	@Id 
	EventId eventId;
	Integer eventDurationMinutes;	
	Set <Seat> seats;
	EventType eventType; 
	String description;
	Set<String> images;
	String cancellationReason;
	String userId;
	
	
	public Event(String eventName, String artist, EventId eventId,
			Integer eventDurationMinutes, Set <Seat> seats, EventType eventType, String description, Set<String> images,
			String userId) {
		this.eventStatus = EventStatus.HIDDEN;
		this.eventName = eventName;
		this.artist = artist;
		this.eventId = eventId;
		this.eventDurationMinutes = eventDurationMinutes;
		this.seats = seats;
		this.eventType = eventType;
		this.description = description;
		this.images = images;
		this.userId = userId;
	}

	public Event(EventArchived eventArchived) {
		this.eventStatus = eventArchived.getEventStatus();
		this.eventName = eventArchived.getEventName();
		this.artist = eventArchived.getArtist();
		this.eventId = eventArchived.getEventId();
		this.eventDurationMinutes = eventArchived.getEventDurationMinutes();
		this.seats = eventArchived.getSeats();
		this.eventType = eventArchived.getEventType();
		this.description = eventArchived.getDescription();
		this.images = eventArchived.getImages();
		this.cancellationReason = eventArchived.getCancellationReason();
		this.userId = eventArchived.getUserId();
	}
	
	public Event(EventCancelled eventCancelled) {
		this.eventStatus = eventCancelled.getEventStatus();
		this.eventName = eventCancelled.getEventName();
		this.artist = eventCancelled.getArtist();
		this.eventId = eventCancelled.getEventId();
		this.eventDurationMinutes = eventCancelled.getEventDurationMinutes();
		this.seats = eventCancelled.getSeats();
		this.eventType = eventCancelled.getEventType();
		this.description = eventCancelled.getDescription();
		this.images = eventCancelled.getImages();
		this.cancellationReason = eventCancelled.getCancellationReason();
		this.userId = eventCancelled.getUserId();
	}
	
	public static Event convertArchivedEventToEvent(EventArchived e) {
		return 	Event.builder()
					.eventStatus(e.getEventStatus())
					.eventName(e.getEventName())
					.artist(e.getArtist())
					.eventId(e.getEventId())
					.eventDurationMinutes(e.getEventDurationMinutes())
					.seats(e.getSeats())
					.eventType(e.getEventType())
					.description(e.getDescription())
					.images(e.getImages())
					.cancellationReason(e.getCancellationReason())
					.userId(e.getUserId())
					.build();
	}
	
}
