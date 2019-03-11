package telran.ashkelon2018.ticket.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.configuration.AccountConfiguration;
import telran.ashkelon2018.ticket.configuration.AccountCredentials;
import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.dto.account.AccountProfileDto;
import telran.ashkelon2018.ticket.dto.account.AccountRegDto;
import telran.ashkelon2018.ticket.enums.UserRole;
import telran.ashkelon2018.ticket.exceptions.UserExistsException;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	UserAccountRepository repository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public AccountProfileDto addManager(AccountRegDto managerRegDto, String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		if (repository.existsById(credentials.getLogin().toLowerCase())) {
			throw new UserExistsException("User already exists");
		}
		String hashPassword = encoder.encode(credentials.getPassword());
		UserAccount manager = UserAccount.builder()
				.login(credentials.getLogin().toLowerCase())
				.password(hashPassword)
				.name(managerRegDto.getName())
				.phone(managerRegDto.getPhone())
				.role(UserRole.MANAGER)
				.role(UserRole.USER)
				.visitedEvents(new HashSet<EventId>())
				.build();  
		repository.save(manager);
		return convertToAccountProfileDto(manager);
	}

	private AccountProfileDto convertToAccountProfileDto(UserAccount manager) {
		return AccountProfileDto.builder()
				.login(manager.getLogin().toLowerCase())
				.name(manager.getName())
				.phone(manager.getPhone())
				.halls(manager.getHalls())
				.visitedEvents(manager.getVisitedEvents())
				.build();
	}

	@Override
	public AccountProfileDto loginAccount(String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = repository.findById(credentials.getLogin().toLowerCase()).get();
		return convertToUniversalAccountProfileDto(userAccount);
	}

	private AccountProfileDto convertToUniversalAccountProfileDto(UserAccount userAccount) {
		return AccountProfileDto.builder()
				.login(userAccount.getLogin().toLowerCase())
				.name(userAccount.getName())
				.phone(userAccount.getPhone())
				.halls(userAccount.getHalls())
				.visitedEvents(userAccount.getVisitedEvents())
				.build();
	}

	@Override
	public AccountProfileDto editManager(AccountRegDto managerRegDto, String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = repository.findById(credentials.getLogin().toLowerCase()).get();
		userAccount.setName(managerRegDto.getName());
		userAccount.setPhone(managerRegDto.getPhone());
		repository.save(userAccount);
		return convertToAccountProfileDto(userAccount);

	}

	@Override
	public AccountProfileDto removeUser(String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = repository.findById(credentials.getLogin().toLowerCase()).get();
		repository.delete(userAccount);
		return convertToAccountProfileDto(userAccount);
	}

	@Override
	public boolean changePassword(String password, String token) {
		try {
			AccountCredentials credentials = accountConfiguration.tokenDecode(token);
			UserAccount userAccount = repository.findById(credentials.getLogin()).get();
			String hashPassword = encoder.encode(password);
			userAccount.setPassword(hashPassword);
			repository.save(userAccount);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public AccountProfileDto addUser(AccountRegDto accountRegDto, String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		if (repository.existsById(credentials.getLogin().toLowerCase())) {
			throw new UserExistsException("User already exists");
		}
		String hashPassword = encoder.encode(credentials.getPassword());
		UserAccount user = UserAccount.builder()
				.login(credentials.getLogin().toLowerCase())
				.password(hashPassword)
				.name(accountRegDto.getName())
				.phone(accountRegDto.getPhone())
				.role(UserRole.USER)
				.visitedEvents(new HashSet<EventId>())
				.halls(new HashSet<String>())
				.build();  
		repository.save(user);
		return convertToAccountProfileDto(user);
	}

}
