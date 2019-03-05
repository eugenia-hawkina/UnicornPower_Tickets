package telran.ashkelon2018.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.dto.account.AccountProfileDto;
import telran.ashkelon2018.ticket.dto.account.AccountRegDto;
import telran.ashkelon2018.ticket.service.AccountService;
@RestController
@RequestMapping("/account")
public class AccountServiceController {

	@Autowired
	AccountService accountService;
	
	
	@PostMapping("/manager/registration")
	public AccountProfileDto addManager(@RequestBody AccountRegDto managerRegDto, 
			@RequestHeader("Authorization") String token) {
		return accountService.addManager(managerRegDto, token);
	}
	
	@PostMapping("/user/registration")
	public AccountProfileDto addUser(@RequestBody AccountRegDto accountRegDto, 
			@RequestHeader("Authorization") String token) {
		return accountService.addUser(accountRegDto, token);
	}
	
	@GetMapping("/login")
	public AccountProfileDto loginAccount(@RequestHeader("Authorization") String token) {
		return accountService.loginAccount(token);
	}
	
	@PutMapping("/update")
	public AccountProfileDto editManager(@RequestBody AccountRegDto managerRegDto, 
			@RequestHeader("Authorization") String token) {		
		return accountService.editManager(managerRegDto, token);
	}

	@DeleteMapping("/user/remove")
	public AccountProfileDto removeUser(@RequestHeader("Authorization") String token) {
		return accountService.removeUser(token);
	}
		
	@PutMapping("/password")
	public boolean changePassword(@RequestHeader("X-Authorization") String password, 
			@RequestHeader("Authorization") String token) {
		return accountService.changePassword(password, token);
	}


}
