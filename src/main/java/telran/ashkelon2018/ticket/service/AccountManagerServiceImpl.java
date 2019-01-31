package telran.ashkelon2018.ticket.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.configuration.AccountConfiguration;
import telran.ashkelon2018.ticket.configuration.AccountCredentials;
import telran.ashkelon2018.ticket.dao.ManagerAccountRepository;
import telran.ashkelon2018.ticket.domain.Manager;
import telran.ashkelon2018.ticket.dto.account.ManagerProfileDto;
import telran.ashkelon2018.ticket.dto.account.ManagerRegDto;
import telran.ashkelon2018.ticket.exceptions.UserExistsException;

@Service
public class AccountManagerServiceImpl implements AccountManagerService {

	@Autowired
	ManagerAccountRepository managerAccountRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public ManagerProfileDto addManager(ManagerRegDto managerRegDto, String token) {
		// FIXME O_o
		AccountCredentials credentials = accountConfiguration.tokenDecode(token);
		if(managerAccountRepository.existsById(credentials.getLogin())) {
			throw new UserExistsException();
		}
		String hashPassword = encoder.encode(credentials.getPassword());
		Manager manager = Manager.builder()
				.login(credentials.getLogin())
				.password(hashPassword)
				.name(managerRegDto.getName())
				.phone(managerRegDto.getPhone())
				.build();
		managerAccountRepository.save(manager);		
		return convertToManagerProfileDto(manager);
	}
	
	private ManagerProfileDto convertToManagerProfileDto(Manager manager) {
		return ManagerProfileDto.builder()
				.login(manager.getLogin())
				.name(manager.getName())
				.phone(manager.getPhone())
				.halls(manager.getHalls())
				.build();
	}
	
	@Override
	public ManagerProfileDto loginManager(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagerProfileDto editManager(ManagerRegDto userRegDto, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagerProfileDto removeManager(String login, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changePassword(String password, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> addHall(String login, String hallId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> removeHall(String login, String hallId, String token) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
