package telran.ashkelon2018.ticket.service;

import telran.ashkelon2018.ticket.dto.account.ManagerAccountProfileDto;
import telran.ashkelon2018.ticket.dto.account.ManagerRegDto;

public interface AccountManagerService {
	ManagerAccountProfileDto addManager(ManagerRegDto managerRegDto, String token);
	ManagerAccountProfileDto loginManager(String token);
	ManagerAccountProfileDto editManager(ManagerRegDto userRegDto, String token);
	ManagerAccountProfileDto removeManager(String token);
	boolean changePassword(String password, String token);
//	Set<String> addHall(String login, String hallId, String token);
//	Set<String> removeHall(String login, String hallId, String token);

}
