package telran.ashkelon2018;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.enums.UserRole;

@SpringBootApplication
public class TicketServiceApplication implements CommandLineRunner {

	@Autowired
	UserAccountRepository repository;

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
	}
}
