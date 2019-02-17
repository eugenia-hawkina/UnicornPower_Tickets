package telran.ashkelon2018.ticket.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import telran.ashkelon2018.ticket.enums.HallType;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "hallId")
@Document(collection="ticketService_Halls")
public class Hall {
	@Id 
	String hallId;
	String locationName;
	String hallName;
	String country;
	String region;
	String city;
	String street;
	String builing;
	HallType hallType;
	Integer maxCapacity; 	//if there are no seats
	@Singular
	Set<String> managers;	// manager's id
	Set<Seat> seats;
	
	
	public Hall(String hallId, String locationName, String hallName, String country, 
			String region, String city, String street, String builing, HallType hallType,
			Integer maxCapacity) {
		this.hallId = hallId;
		this.locationName = locationName;
		this.hallName = hallName;
		this.country = country;
		this.region = region;
		this.city = city;
		this.street = street;
		this.builing = builing;
		this.hallType = hallType;
		this.maxCapacity = maxCapacity;
	}
	
	public void addManager(String manager) {
		managers.add(manager);
	}
	
	public void removeManager(String manager) {
		managers.remove(manager);
	}
}


