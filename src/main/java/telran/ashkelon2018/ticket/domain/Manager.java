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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="login")
@Document(collection="ticketService_Users")
public class Manager {
	@Id
	String login;
	@NotNull
	String password;
	String name;
	String phone;
	@Singular
	Set<String> halls; // hall ids
	
	public Manager(String login, @NotNull String password) {
		this.login = login;
		this.password = password;
	}
	
	public Manager(String login, @NotNull String password, String name, String phone) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}
	
	public void addHall(String hallId) {
		halls.add(hallId);
	}
	
	public void removeHall(String hallId) {
		halls.remove(hallId);
	}
	
}
