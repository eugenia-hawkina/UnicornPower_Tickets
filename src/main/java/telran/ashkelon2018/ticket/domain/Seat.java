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
@EqualsAndHashCode(of= {"seatId"})
public class Seat {
	@Id 
	SeatId seatId;
	PriceRange priceRange;
	boolean availability;	
	long bookingTime;
	boolean paid;
	String buyerInfo;
	
	
	public Seat(SeatId seatId, PriceRange priceRange, boolean availability, boolean paid, String buyerInfo) {
		this.seatId = seatId;
		this.priceRange = priceRange;
		this.availability = true;
		this.paid = false;
		this.buyerInfo = "free";
	}

	
}
