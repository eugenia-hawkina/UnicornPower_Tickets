package telran.ashkelon2018.ticket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
//	@Override
//	public void configure(WebSecurity web) {
//		web.ignoring().antMatchers("/event*/**");
//	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		// FIXME - session creation ??
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		http.authorizeRequests().antMatchers("/event*/**").permitAll();
		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
		
	}
}
