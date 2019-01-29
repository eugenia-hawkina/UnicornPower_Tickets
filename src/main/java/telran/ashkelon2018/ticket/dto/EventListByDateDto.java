package telran.ashkelon2018.ticket.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventListByDateDto {
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateFrom;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateTo;
	String hallId;

}
