package telran.ashkelon2018.ticket.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.ticket.domain.EventId;
import telran.ashkelon2018.ticket.domain.Seat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPurchaseDto {

	EventId eventId;
	Set <Seat> seats;
	String login;
}
