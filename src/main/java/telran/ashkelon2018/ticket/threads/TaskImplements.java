//package telran.ashkelon2018.ticket.threads;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//
//import telran.ashkelon2018.ticket.dao.EventRepository;
//import telran.ashkelon2018.ticket.domain.Event;
//import telran.ashkelon2018.ticket.domain.EventId;
//import telran.ashkelon2018.ticket.domain.Seat;
//import telran.ashkelon2018.ticket.service.TicketServiceUserImpl;
//
//public class TaskImplements implements Runnable {
//
//	@Autowired
//	MongoTemplate mongoTemplate;
//
//	@Autowired
//	EventRepository eventRepository;
//
//	Seat seat;
//	EventId eventId;
//
//	public TaskImplements(Seat seat, EventId eventId) {
//		this.seat = seat;
//		this.eventId = eventId;
//	}
//
//	@Override
//	public void run() {
//		try {
//			Thread.sleep(10000);
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		// FIXME doesn't work too
////		TicketServiceUserImpl aImpl = new TicketServiceUserImpl();
////		aImpl.cancelTmpBookedTicket(eventId, seat);
//
//		
////		 // FIXME this doesn't work :(
////		 if(!seat.isPaid()) {
////		 System.out.println(eventId.toString());
////		 Event event = eventRepository.findById(eventId).orElse(null);
////		 if(event == null) {
////		 System.out.println("shit :(");
////		 }
////		 Query query = new Query();
////		 Criteria criteria = Criteria.where("_id").is(eventId).and("seats").is(seat);
////		 query.addCriteria(criteria);
////		 Update update = new Update();
////		 update.set("seats.$.availability", "true").set("seats.$.buyerInfo", "free");
////		 event = mongoTemplate.findAndModify(query, update, Event.class);
////		 System.out.println(seat.toString());
////		 }
//	}
//
//}
