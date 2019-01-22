package telran.ashkelon2018.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.domain.PriceRange;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
	String block;
	String row;
	String seatNumber;
	PriceRange priceRange;

}
