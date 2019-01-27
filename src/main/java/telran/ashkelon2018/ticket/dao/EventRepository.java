package telran.ashkelon2018.ticket.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;


import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;

//FIXME event id or string?
public interface EventRepository extends MongoRepository<Event, EventId> {
	
	Stream<Event> findAllBy();
	
	Iterable<Event> findByUserId(String userId);
	
}
