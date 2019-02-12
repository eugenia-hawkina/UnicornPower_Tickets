package telran.ashkelon2018.ticket.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(of="seatId")
public class Seat {
	@Id 
	SeatId seatId;
	PriceRange priceRange;
	boolean availability;	// for booking
	long bookingTime;
	boolean paid;
	BuyerInfo buyerInfo;
	
	
	public Seat(SeatId seatId, PriceRange priceRange, boolean availability, boolean paid) {
		this.seatId = seatId;
		this.priceRange = priceRange;
		this.availability = true;
		this.paid = false;
	} 
	
	
}
