package telran.ashkelon2018.ticket.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.enums.EventType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSearchDto {
	LocalDate dateFrom;
	LocalDate dateTo;
	String artist;
	EventType eventType;
	String hallId;

}
