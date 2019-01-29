package telran.ashkelon2018.ticket.service;

import java.util.Set;

import telran.ashkelon2018.ticket.dto.UserProfileDto;
import telran.ashkelon2018.ticket.dto.UserRegDto;

public interface AccountService {
	UserProfileDto addUser(UserRegDto userRegDto, String token);
	UserProfileDto loginUser(String token);
	UserProfileDto editUsur(UserRegDto userRegDto, String token);
	UserProfileDto removeUser(String login, String token);
	Set<String> addRole(String login, String role, String token);
	Set<String> removeRole(String login, String role, String token);
	boolean changePassword(String password, String token);

}
