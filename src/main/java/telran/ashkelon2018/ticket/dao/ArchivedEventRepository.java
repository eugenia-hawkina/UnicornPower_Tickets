package telran.ashkelon2018.ticket.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventId;

public interface ArchivedEventRepository extends MongoRepository<EventArchived, EventId> {

	Stream<EventArchived> findAllBy();
	
	Iterable<EventArchived> findByUserId(String userId);
}
