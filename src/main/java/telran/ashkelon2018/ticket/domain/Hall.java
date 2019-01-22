package telran.ashkelon2018.ticket.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.ticket.enums.HallType;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "hallId")
@Document(collection="ticketService_halls")
public class Hall {
	@Id String hallId;
	String locationName;
	String hallName;
	String country;
	String region;
	String city;
	String street;
	String builing;
	HallType hallType;
	Integer capacity;
	Set<String> managers;	// manager's id
	Set<Seat> seats;
	
	
	public Hall(String hallId, String locationName, String hallName, String country, 
			String region, String city, String street, String builing, HallType hallType, 
			Set<Seat> seats) {
		this.hallId = hallId;
		this.locationName = locationName;
		this.hallName = hallName;
		this.country = country;
		this.region = region;
		this.city = city;
		this.street = street;
		this.builing = builing;
		this.hallType = hallType;
		this.seats = seats;
	}
	
	
}


