package telran.ashkelon2018.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.configuration.AccountConfiguration;
import telran.ashkelon2018.ticket.configuration.AccountCredentials;
import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;
import telran.ashkelon2018.ticket.dto.account.ManagerRegDto;
import telran.ashkelon2018.ticket.enums.UserRole;
import telran.ashkelon2018.ticket.exceptions.UserExistsException;

@Service
public class AccountManagerServiceImpl implements AccountManagerService {

	@Autowired
	UserAccountRepository repository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public ManagerAccountProfileDto addManager(ManagerRegDto managerRegDto, String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		if (repository.existsById(credentials.getLogin())) {
			throw new UserExistsException("User already exists");
		}
		String hashPassword = encoder.encode(credentials.getPassword());
		UserAccount manager = UserAccount.builder()
				.login(credentials.getLogin())
				.password(hashPassword)
				.name(managerRegDto.getName())
				.phone(managerRegDto.getPhone())
				.role(UserRole.MANAGER)
				.role(UserRole.USER)
				.build();  
		repository.save(manager);
		return convertToManagerAccountProfileDto(manager);
	}

	private ManagerAccountProfileDto convertToManagerAccountProfileDto(UserAccount manager) {
		return ManagerAccountProfileDto.builder().login(manager.getLogin()).name(manager.getName())
				.phone(manager.getPhone()).halls(manager.getHalls()).build();
	}

	@Override
	public ManagerAccountProfileDto loginManager(String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = repository.findById(credentials.getLogin()).get();
		return convertToManagerAccountProfileDto(userAccount);
	}

	@Override
	public ManagerAccountProfileDto editManager(ManagerRegDto managerRegDto, String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = repository.findById(credentials.getLogin()).get();
		userAccount.setName(managerRegDto.getName());
		userAccount.setPhone(managerRegDto.getPhone());
		repository.save(userAccount);
		return convertToManagerAccountProfileDto(userAccount);

	}

	@Override
	public ManagerAccountProfileDto removeManager(String token) {
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = repository.findById(credentials.getLogin()).get();
		repository.delete(userAccount);
		return convertToManagerAccountProfileDto(userAccount);
	}

	@Override
	//FIXME doesn't work with principal
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

//	@Override
//	public Set<String> addHall(String login, String hallId, String token) {	
//		return null;
//	}
//
//	@Override
//	public Set<String> removeHall(String login, String hallId, String token) {		
//		return null;
//	}

}
