package com.pruthvi.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.pruthvi.service.UserInfoDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	DataSource dataSource;

	/**
	 * Authentication Stage
	 * 
	 * @implNote UserDetailsService is responsible for configuring the user details
	 *           and there roles
	 * @param encoder
	 * @return
	 */
	@Bean
	UserDetailsService loadUserDetailservice(PasswordEncoder encoder) {
		/***
		 * 1. This is In-memory authentication for hardcoded users and there password
		 * Practically we should'nt hard code user's
		 * 
		 **/
//		UserDetails admin = User.withUsername("john").password(encoder.encode("pwd")).roles("ADMIN").build();
//		UserDetails user = User.withUsername("smith").password(encoder.encode("pwd")).roles("USER").build();
//		UserDetails employee = User.withUsername("andy").password(encoder.encode("pwd")).roles("STAFF").build();
//		return new InMemoryUserDetailsManager(admin, user,employee);

		/***
		 * 2. UserInfoDetailsService will return UserDetails Accessing Username &
		 * Password using DB
		 */
		return new UserInfoDetailsService();

	}

	/**
	 * Used for JDBC Authentication Method
	 * 
	 * @param encoder
	 * @return
	 */
	@Bean
	JdbcUserDetailsManager loadUserForJdbc(PasswordEncoder encoder) {
		/**
		 * 3. JDBC Authentication Method Makes use of Schema.sql for Creating Table user
		 * & Authority Make sure you comment authenticateProvider() line 124
		 * 
		 */
		var jdbcbuilderManager = new JdbcUserDetailsManager(dataSource);
		var admin = User.builder().username("john").password(encoder.encode("pwd")).roles("ADMIN").build();
		var staff = User.builder().username("andy").password(encoder.encode("pwd")).roles("STAFF").build();
		var user = User.builder().username("smith").password(encoder.encode("pwd")).roles("USER").build();
		jdbcbuilderManager.createUser(admin);
		jdbcbuilderManager.createUser(staff);
		jdbcbuilderManager.createUser(user);
		return jdbcbuilderManager;
	}

	/**
	 * @implNote Used to Encode the password
	 * @return
	 */
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Authorization Stage
	 * 
	 * @implNote Security Filer chain is used add Filters to EndPoints (API) (ex :
	 *           Home screen can be access by all users with Role User)
	 * @param https
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain configureRequest(HttpSecurity https) throws Exception {
		https.csrf(Customizer.withDefaults())
				.authorizeHttpRequests(t -> t.requestMatchers("/Home/**", "/AddUser/**").permitAll()
						.requestMatchers("/Admin/**", "/EmployeeDashBoard/**").authenticated())
				.httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());

		return https.build();
	}

	/**
	 * Responsible in calling UserInfoDetailsService class (fetching user
	 * information from a specific repository) , Comment loadUserDetailservice if
	 * you want to Perform JDBC authentication else Comment loadUserForJdbc for
	 * other Authentication
	 * 
	 * @return
	 */
	@Bean
	AuthenticationProvider authenticateProvider() {
		var authenticateProvider = new DaoAuthenticationProvider();
		/** Used For In Memory / UserDetailservice Authentication Methods **/
//		authenticateProvider.setUserDetailsService(loadUserDetailservice(encoder()));
		/*** End **/

		/** Used for JDBC Authentication Method ***/
		authenticateProvider.setUserDetailsService(loadUserForJdbc(encoder()));
		/*** End **/
		authenticateProvider.setPasswordEncoder(encoder());
		return authenticateProvider;
	}
}
