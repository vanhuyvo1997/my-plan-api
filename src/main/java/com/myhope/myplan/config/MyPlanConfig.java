package com.myhope.myplan.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.myhope.myplan.domain.PlanUser;
import com.myhope.myplan.domain.PlanUserRole;
import com.myhope.myplan.service.PlanUserRoles;
import com.myhope.myplan.service.userservice.PlanUserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class MyPlanConfig {
	
	private final PlanUserService planUserService;
	private Environment env;

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			PlanUser user = new PlanUser();
			PlanUserRole role = new PlanUserRole();
			
			List<PlanUserRole> roles = new ArrayList<>();
			roles.add(role);
			
			role.setName(PlanUserRoles.ADMIN.name());
			
			user.setUsername(env.getProperty("myplan.admin.username"));
			user.setPassword(env.getProperty("myplan.admin.password"));
			user.setName(env.getProperty("myplan.admin.name"));
			user.setVerified(true);
			user.setEnabled(true);
			user.setPlanUserRoles(roles);
			
			planUserService.savePlanUser(user);
		};
	}
}
