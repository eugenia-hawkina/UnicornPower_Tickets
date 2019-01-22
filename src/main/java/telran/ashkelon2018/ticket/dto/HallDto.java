package telran.ashkelon2018.ticket.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import telran.ashkelon2018.ticket.dto.SeatDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HallDto {
	@NonNull String hallId;
	@Singular Set<SeatDto> seatDtos;

}
