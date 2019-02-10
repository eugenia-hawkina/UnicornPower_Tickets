package telran.ashkelon2018.ticket.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class UserHasNotRightsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserHasNotRightsException(String message) {
		super(message);
	}

}
