package telran.ashkelon2018.ticket.dto.account;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.domain.EventId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountProfileDto {
	String login;
	String name;
	String phone;
	Set<String> halls;	
	Set <EventId> visitedEvents;
}
