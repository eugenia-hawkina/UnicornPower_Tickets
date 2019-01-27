package telran.ashkelon2018.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.domain.PriceRange;
import telran.ashkelon2018.ticket.domain.SeatId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
	SeatId seatId;
	PriceRange priceRange;

}
