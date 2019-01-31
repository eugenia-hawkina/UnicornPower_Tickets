package telran.ashkelon2018.ticket.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ticket.dto.account.ManagerProfileDto;
import telran.ashkelon2018.ticket.dto.account.ManagerRegDto;
import telran.ashkelon2018.ticket.service.AccountManagerService;
@RestController
@RequestMapping("/account/manager")
public class AccountManagerServiceController {

	@Autowired
	AccountManagerService accountManagerService;
	
	
	@PostMapping("/register")
	public ManagerProfileDto addManager(@RequestBody ManagerRegDto managerRegDto, 
			@RequestHeader("Authorization") String token) {
		return accountManagerService.addManager(managerRegDto, token);
	}
	
	@GetMapping
	public ManagerProfileDto loginManager(@RequestHeader("Authorization") String token) {
		return accountManagerService.loginManager(token);
	}
	
	@PutMapping("/update/{login}")
	public ManagerProfileDto editManager(@RequestBody ManagerRegDto managerRegDto, 
			@RequestHeader("Authorization") String token) {
		
		return accountManagerService.editManager(managerRegDto, token);
	}

	@DeleteMapping("/{login}")
	public ManagerProfileDto removeManager(@PathVariable String login, 
			@RequestHeader("Authorization") String token) {
		return accountManagerService.removeManager(login, token);
	}
		
	@PutMapping("/password")
	public boolean changePassword(@RequestHeader("X-Authorization") String password, 
			@RequestHeader("Authorization") String token) {
		// FIXME what is x-auth?
		return accountManagerService.changePassword(password, token);
	}

	@PutMapping("/{login}/{hallId}")
	Set<String> addHall(@PathVariable String login, @PathVariable String hallId, 
			@RequestHeader("Authorization") String token){
		return accountManagerService.addHall(login, hallId, token);
	}
	
	@DeleteMapping("/{login}/{hallId}")
	Set<String> removeHall(@PathVariable String login, @PathVariable String hallId, 
			@RequestHeader("Authorization") String token){
		return accountManagerService.removeHall(login, hallId, token);
	}


}
