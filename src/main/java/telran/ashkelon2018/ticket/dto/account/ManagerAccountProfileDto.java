package telran.ashkelon2018.ticket.dto.account;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerAccountProfileDto {
	String login;
	String name;
	String phone;
//	Set <EventId> visitedEvents;
	Set<String> halls;
	

//	public ManagerAccountProfileDto(String login, Set<EventId> visitedEvents) {
//	//	for user
//		this.login = login;
//		this.visitedEvents = visitedEvents;
//	}
	
	
}

