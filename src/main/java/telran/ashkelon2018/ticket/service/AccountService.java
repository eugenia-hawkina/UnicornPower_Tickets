package telran.ashkelon2018.ticket.service;

import telran.ashkelon2018.ticket.dto.account.AccountProfileDto;
import telran.ashkelon2018.ticket.dto.account.AccountRegDto;

public interface AccountService {
	
	//for all
	AccountProfileDto loginAccount(String token);
	
	AccountProfileDto editManager(AccountRegDto userRegDto, String token);	

	boolean changePassword(String password, String token);
	
	//manager
	AccountProfileDto addManager(AccountRegDto accountRegDto, String token);
	
	AccountProfileDto removeUser(String token);

	// user
	AccountProfileDto addUser(AccountRegDto accountRegDto, String token);

}
