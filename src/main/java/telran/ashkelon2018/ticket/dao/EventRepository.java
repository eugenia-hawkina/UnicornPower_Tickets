package telran.ashkelon2018.ticket.dao;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.EventType;

public interface EventRepository extends MongoRepository<Event, EventId> {
	
	Stream<Event> findAllBy();
	
	Set<Event> findByUserIdOrderByEventIdEventStart(String userId);
	
	Stream<Event> findByEventStatusOrderByEventIdEventStart(EventStatus eventStatus);
	
	Stream<Event> findByEventIdHallIdAndEventIdEventStartBetweenOrderByEventIdEventStart(String hallId, LocalDate from, LocalDate to);
	
	Stream<Event> findByEventIdEventStartBetweenOrderByEventIdEventStart(LocalDate from, LocalDate to);
	
	Stream<Event> findByEventIdHallIdOrderByEventIdEventStart(String hallId);
	
	Stream<Event> findByArtistOrderByEventIdEventStart(String artist);
	
	Stream<Event> findByEventIdEventStartBeforeOrderByEventIdEventStart(LocalDate to);

	Stream<Event> findByEventTypeOrderByEventIdEventStart(EventType eventType);
	
}
