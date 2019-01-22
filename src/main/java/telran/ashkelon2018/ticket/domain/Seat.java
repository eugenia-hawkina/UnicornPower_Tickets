package telran.ashkelon2018.ticket.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of="seatId")
public class Seat {
	@Id SeatId seatId;
	AccessType accessType;
	PriceRange priceRange;
	boolean availability;
	boolean paid;
	long bookingTime;
	
	
	public Seat(SeatId seatId, AccessType accessType, boolean availability, boolean paid) {
		this.seatId = seatId;
		this.accessType = accessType;
		this.availability = true;
		this.paid = false;
	} 
	
	
}
