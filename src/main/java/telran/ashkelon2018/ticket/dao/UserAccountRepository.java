package telran.ashkelon2018.ticket.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ticket.domain.UserAccount;
import telran.ashkelon2018.ticket.enums.UserRole;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
	
	Stream<UserAccount> findAllBy();
	
	Stream<UserAccount> findAllByOrderByLogin();
	
	Stream<UserAccount> findManagersByRolesInOrderByLogin(UserRole userRole);

}
