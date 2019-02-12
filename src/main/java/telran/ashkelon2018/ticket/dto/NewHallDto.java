package telran.ashkelon2018.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.enums.HallType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewHallDto {

	String hallId;
	String locationName;
	String hallName;
	String country;
	String region;
	String city;
	String street;
	String builing;
	HallType hallType;
	Integer maxCapacity;
}
