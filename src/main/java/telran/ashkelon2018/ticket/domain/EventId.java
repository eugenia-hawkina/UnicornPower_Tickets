package telran.ashkelon2018.ticket.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @Embeddable - for sql

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH-mm")
	LocalDateTime eventStart; 	
	String hallId;

}
