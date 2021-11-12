package com.myhope.myplan.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myhope.myplan.config.security.filter.CustomAuthorizationFilter;
import com.myhope.myplan.config.security.filter.CustumAuthenticationFilter;
import com.myhope.myplan.service.PlanUserRoles;
import com.myhope.myplan.service.userservice.PlanUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class MyPlanSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PlanUserService planUserService;
	private final PasswordEncoder passwordEncoder;
	private final Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		var custumAuthenticationFilter = new CustumAuthenticationFilter(authenticationManagerBean(), env);
		custumAuthenticationFilter.setFilterProcessesUrl("/api/login");

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers("/api/login/**", "/api/token/refresh/**", "/api/register/**").permitAll()

//				.antMatchers("/api/management/user")
//					.hasAnyAuthority("ROLE_ADMIN")

				.antMatchers("/api/plan/**")
					.hasRole(PlanUserRoles.USER.name())

				.anyRequest().authenticated();

		http.addFilter(custumAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(env), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.planUserService).passwordEncoder(this.passwordEncoder);
	}
}
