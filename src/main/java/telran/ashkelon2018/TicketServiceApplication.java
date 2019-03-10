package telran.ashkelon2018;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.ashkelon2018.ticket.dao.EventArchivedRepository;
import telran.ashkelon2018.ticket.dao.EventCancelledRepository;
import telran.ashkelon2018.ticket.dao.EventRepository;
import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.Event;
import telran.ashkelon2018.ticket.domain.EventArchived;
import telran.ashkelon2018.ticket.domain.EventCancelled;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.enums.EventStatus;
import telran.ashkelon2018.ticket.enums.UserRole;

@SpringBootApplication
public class TicketServiceApplication implements CommandLineRunner {

	@Autowired
	UserAccountRepository repository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventArchivedRepository eventArchivedRepository;
	
	@Autowired
	EventCancelledRepository eventCancelledRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!repository.existsById("owner@mail.ru")) {
			// create first OWNER
			String hashPassword = encoder.encode("owner");
			UserAccount owner = UserAccount.builder().login("owner@mail.ru")
					.password(hashPassword)
					.name("Owner")
					.phone("666")
					.role(UserRole.OWNER)
					.role(UserRole.MANAGER)
					.role(UserRole.USER)
					.build();
			repository.save(owner);
		}
		
		
		TaskImpl task = new TaskImpl();
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		
		thread.start();
		
	}
	
	
	private class TaskImpl implements Runnable{

		@Override
		public void run() {			
			while (true) {
				try {
					LocalDate date = LocalDate.now(); 
					Event[] eventsArr = eventRepository.findByEventIdEventStartBefore(date)
							.toArray(Event[]::new);
					for(int i = 0; i < eventsArr.length; i++) {
						if(eventsArr[i].getEventStatus().equals(EventStatus.HIDDEN)) {
							EventCancelled eventCancelled = convertEventToEventCancelled(eventsArr[i]);
							eventCancelledRepository.save(eventCancelled);
							eventRepository.delete(eventsArr[i]);
						} else {
						EventArchived eventArchived = convertEventToEventArchived(eventsArr[i]);
						eventArchivedRepository.save(eventArchived);
						eventRepository.delete(eventsArr[i]);
						}
					}
					
					LocalTime time = LocalTime.now();
					long hour = time.getHour();
					long min = time.getMinute();
					long delayMin = (24 - hour) * 60 - (60 - min); 
					long delayMillis = delayMin * 60 * 1000;
					Thread.sleep(delayMillis);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				} 
			} 			
		}

		private EventCancelled convertEventToEventCancelled(Event event) {			
			return EventCancelled.builder()
					.eventStatus(EventStatus.CANCELLED)
					.eventName(event.getEventName())
					.artist(event.getArtist())
					.eventId(event.getEventId())
					.eventDurationMinutes(event.getEventDurationMinutes())
					.seats(event.getSeats())
					.eventType(event.getEventType())
					.description(event.getDescription())
					.images(event.getImages())
					.cancellationReason("Expiration of a time limit - not approved")
					.userId("Autocancelled")
					.build();			
		}

		private EventArchived convertEventToEventArchived(Event event) {			
			return EventArchived.builder()
					.eventStatus(EventStatus.ARCHIVED)
					.eventName(event.getEventName())
					.artist(event.getArtist())
					.eventId(event.getEventId())
					.eventDurationMinutes(event.getEventDurationMinutes())	
					.seats(event.getSeats())
					.eventType(event.getEventType())
					.description(event.getDescription())
					.images(event.getImages())
					.cancellationReason(event.getCancellationReason())
					.userId(event.getUserId())
					.build();
		}			
	}
}
