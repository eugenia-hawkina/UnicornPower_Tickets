package telran.ashkelon2018.ticket.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;

public class TaskImplements implements Runnable {
	// имплементирует интерфейс ->
	// сначала нужно создать объект класса, потом запихнуть его в новый сред и потом
	// старт

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	EventRepository eventRepository;
	
	Seat seat;
	EventId eventId;

	
	public TaskImplements(Seat seat, EventId eventId) {
		this.seat = seat;
		this.eventId = eventId;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(15000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!seat.isPaid()) {
			System.out.println(eventId.toString());
			Event event = eventRepository.findById(eventId).orElse(null);
			if(event == null) {
				System.out.println("ass");
			}
			Query query = new Query();
			Criteria criteria = Criteria.where("_id").is(eventId).and("seats").is(seat);
			query.addCriteria(criteria);
			Update update = new Update();
			update.set("seats.$.availability", "true").set("seats.$.buyerInfo", "free");
//			System.out.println("query " + query.toString());
//			System.out.println("criteria " + criteria.toString());
//			System.out.println("update " + update.toString());
			System.out.println("event " + event.toString());
//			System.out.println("eventId " + eventId.toString());
//			System.out.println("seat " + seat.toString());
			
			event = mongoTemplate.findAndModify(query, update, Event.class);
			System.out.println(seat.toString());
		}
	}

}
