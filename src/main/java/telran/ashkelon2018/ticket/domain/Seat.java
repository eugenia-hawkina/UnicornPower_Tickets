package telran.ashkelon2018.ticket.domain;

import telran.ashkelon2018.ticket.dto.PriceRange;
import telran.ashkelon2018.ticket.enums.AccessType;

public class Seat {
	String block;
	String row;
	String seatNumber;
	AccessType accessType;
	PriceRange priceRange;
	boolean availability;
}
