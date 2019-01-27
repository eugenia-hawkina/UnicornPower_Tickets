package telran.ashkelon2018.ticket.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.ticket.enums.AccessType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeatId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String block;
	String row;
	String seatNumber;
	AccessType accessType;
	
}
