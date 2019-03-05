package telran.ashkelon2018.ticket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import telran.ashkelon2018.ticket.enums.UserRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	 
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.POST, "/account/manager/registration");
		web.ignoring().antMatchers(HttpMethod.POST, "/account/user/registration");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 
		// account requests
		http.authorizeRequests()
			.antMatchers("/account/login")
			.hasRole(UserRole.USER.name());
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.PUT, "/account/password")
			.hasRole(UserRole.USER.name());
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.PUT, "/account/update")
			.hasRole(UserRole.USER.name());		

		http.authorizeRequests()
			.antMatchers(HttpMethod.DELETE, "/account/user/remove")
			.permitAll();
		
		
		// all
		http.authorizeRequests().antMatchers("/event/**").permitAll();	
		http.authorizeRequests().antMatchers("/events/**").permitAll();
		
		// owner 
		http.authorizeRequests().antMatchers("/owner/**").hasRole(UserRole.OWNER.name());
				
		// manager 
		http.authorizeRequests().antMatchers("/manager/**").hasRole(UserRole.MANAGER.name());
		
		// user 
		http.authorizeRequests().antMatchers("/user/**").hasRole(UserRole.USER.name());

	}
}
