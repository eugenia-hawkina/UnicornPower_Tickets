package telran.ashkelon2018.ticket.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ticket.domain.EventCancelled;
import telran.ashkelon2018.ticket.domain.EventId;

public interface EventCancelledRepository extends MongoRepository<EventCancelled, EventId> {

	Stream<EventCancelled> findAllBy();
	
	Iterable<EventCancelled> findByUserId(String userId);
}
