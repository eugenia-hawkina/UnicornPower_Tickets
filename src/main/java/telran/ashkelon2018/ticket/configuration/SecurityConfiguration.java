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
//		web.ignoring().antMatchers("/event*/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/account/manager/registration");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		// FIXME - session creation ??
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/user/**").permitAll();
		 
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/account/manager").hasRole(UserRole.MANAGER.name());
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/account/manager/update").hasRole(UserRole.MANAGER.name());
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/account/manager/password").hasRole(UserRole.MANAGER.name());
		// FIXME permit psw change to all users
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/account/manager/remove").hasRole(UserRole.MANAGER.name());
		http.authorizeRequests().antMatchers("/owner/**").hasRole(UserRole.OWNER.name());
	}
}
