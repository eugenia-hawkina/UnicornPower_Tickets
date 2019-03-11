package telran.ashkelon2018.ticket.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ticket.dao.UserAccountRepository;
import telran.ashkelon2018.ticket.domain.UserAccount;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	UserAccountRepository repository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserAccount userAccount = repository.findById(login.toLowerCase())
				.orElseThrow(() -> new UsernameNotFoundException(login + " not found"));
		String password = userAccount.getPassword();
		//Set<UserRole> rolesSet = userAccount.getRoles();
		//String[] roles = new String[rolesSet.size()];
		String[] roles = userAccount.getRoles().stream()
				//.map(UserRole::name)
				.map(r -> "ROLE_" + r.name())
				.toArray(String[] :: new);
		return new User(login, password, AuthorityUtils.createAuthorityList(roles));
	}

}
