package telran.ashkelon2018.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.domain.EventId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCancellationDto {
	EventId eventId;
	String reason;

}
