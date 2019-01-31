package telran.ashkelon2018;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.ashkelon2018.ticket.dao.ManagerAccountRepository;
import telran.ashkelon2018.ticket.domain.Manager;

@SpringBootApplication
public class TicketServiceApplication implements CommandLineRunner {

	@Autowired
	ManagerAccountRepository repository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!repository.existsById("admin@admin")) {
			// create first admin
			String hashPassword = encoder.encode("admin");
			Manager admin = Manager.builder().login("admin@admin").password(hashPassword).name("Admin").phone("666")
					.build();
			repository.save(admin);
		}
	}
}
