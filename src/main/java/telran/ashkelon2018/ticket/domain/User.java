package telran.ashkelon2018.ticket.domain;

import java.util.Set;

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
@EqualsAndHashCode(of="userId")
@Document(collection="ticketService_Users")
public class User {
	@Id
	String userId;	// mail
	String password;
	@Singular
	Set<UserRole> roles;
	@Singular
	Set<String> halls; // hall ids
	
	public void addRole(UserRole role) {
		roles.add(role);
	}
	
	public void removeRole(UserRole role) {
		roles.remove(role);
	}
	
	public void addHall(String hallId) {
		halls.add(hallId);
	}
	
	public void removeHall(String hallId) {
		halls.remove(hallId);
	}
	
}
