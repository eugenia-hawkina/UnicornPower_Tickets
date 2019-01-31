package telran.ashkelon2018.ticket.service;

import java.util.Set;

import telran.ashkelon2018.ticket.dto.account.ManagerProfileDto;
import telran.ashkelon2018.ticket.dto.account.ManagerRegDto;

public interface AccountManagerService {
	ManagerProfileDto addManager(ManagerRegDto managerRegDto, String token);
	ManagerProfileDto loginManager(String token);
	ManagerProfileDto editManager(ManagerRegDto userRegDto, String token);
	ManagerProfileDto removeManager(String login, String token);
	boolean changePassword(String password, String token);
	Set<String> addHall(String login, String hallId, String token);
	Set<String> removeHall(String login, String hallId, String token);

}
