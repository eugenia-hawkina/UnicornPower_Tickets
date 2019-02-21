package telran.ashkelon2018.ticket.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventId;

public interface EventArchivedRepository extends MongoRepository<EventArchived, EventId> {

	Stream<EventArchived> findAllBy();
	
	Iterable<EventArchived> findByUserId(String userId);
	
	Stream<EventArchived> findByEventIdHallIdAndEventIdEventStartBetween(String hallId, LocalDate from, LocalDate to);
	
	Stream<EventArchived> findByEventIdEventStartBetween(LocalDate dateFrom, LocalDate dateTo);
	
	
}
