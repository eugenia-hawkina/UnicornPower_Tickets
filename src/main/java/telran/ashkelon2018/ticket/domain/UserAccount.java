package telran.ashkelon2018.ticket.domain;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import telran.ashkelon2018.ticket.enums.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="login")
@Document(collection="ticketService_Users")
public class UserAccount {
	@Id
	String login;
	@NotNull
	String password;
	String name;
	String phone;
	@Singular
	Set<String> halls; // hall ids
	@Singular
	Set<UserRole> roles;
	Set <EventId> visitedEvents;
	
	public UserAccount(String login, @NotNull String password) {
		this.login = login;
		this.password = password;
	}
	
	public UserAccount(String login, @NotNull String password, String name, String phone) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}
	
	public UserAccount(String login, @NotNull String password, String name, String phone, Set<UserRole> roles) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.roles = roles;
	}
	
	public void addHall(String hallId) {
		halls.add(hallId);
	}
	
	public void removeHall(String hallId) {
		halls.remove(hallId);
	}
	
	public void addRole(UserRole role) {
		roles.add(role);
	}
	
	public void removeRole(UserRole role) {
		roles.remove(role);
	}
	
	public void addVisitedEvent(EventId event) {
		visitedEvents.add(event);
	}
	
	public void removeVisitedEvent(EventId event) {
		visitedEvents.remove(event);
	}

	
}
