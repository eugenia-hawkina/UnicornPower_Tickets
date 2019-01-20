package telran.ashkelon2018.ticket.domain;

import java.util.Set;

import javax.persistence.Id;

import telran.ashkelon2018.ticket.enums.HallType;

public class Hall {
	@Id String hallId;
	String locationName;
	String hallName;
	String country;
	String region;
	String city;
	String street;
	String builing;
	HallType hallType;
	Integer capacity;
	Set<String> managers;	// manager's id
	Set<Seat> seats;

}
