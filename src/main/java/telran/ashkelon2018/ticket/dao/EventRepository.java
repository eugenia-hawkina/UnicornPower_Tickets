package telran.ashkelon2018.ticket.dao;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.enums.EventStatus;

public interface EventRepository extends MongoRepository<Event, EventId> {
	
	Stream<Event> findAllBy();
	
	Set<Event> findByUserId(String userId);
	
	Stream<Event> findByEventStatus(EventStatus eventStatus);
	
	Stream<Event> findByEventIdHallIdAndEventIdEventStartBetween(String hallId, LocalDate from, LocalDate to);
	
	Stream<Event> findByEventIdEventStartBetween(LocalDate from, LocalDate to);
	
	Stream<Event> findByEventIdHallId(String hallId);
	
	Stream<Event> findByArtist(String artist);
	
}
