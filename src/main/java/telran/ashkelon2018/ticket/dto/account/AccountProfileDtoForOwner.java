package telran.ashkelon2018.ticket.dto.account;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.enums.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountProfileDtoForOwner {
	String login;
	String name;
	String phone;
	Set<UserRole> roles;
	Set <EventId> visitedEvents;
	Set<String> halls;

}
