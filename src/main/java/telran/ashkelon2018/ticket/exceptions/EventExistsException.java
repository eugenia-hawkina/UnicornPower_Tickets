package telran.ashkelon2018.ticket.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class EventExistsException extends RuntimeException{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EventExistsException(String message) {
		super(message);
	}
	
	

}
