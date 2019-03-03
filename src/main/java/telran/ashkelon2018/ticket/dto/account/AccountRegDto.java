package telran.ashkelon2018.ticket.dto.account;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRegDto {
	@NotNull
	String name;
	@NotNull
	String phone;
}
