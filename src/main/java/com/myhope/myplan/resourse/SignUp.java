package com.myhope.myplan.resourse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhope.myplan.domain.PlanUser;
import com.myhope.myplan.domain.PlanUserRole;
import com.myhope.myplan.service.PlanUserRoles;
import com.myhope.myplan.service.userservice.PlanUserService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class SignUp {
	private final PlanUserService planUserService;

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody PlanUser user) {
		if(user.getPlanUserRoles().isEmpty()) {
			user.getPlanUserRoles().add(new PlanUserRole(null, PlanUserRoles.USER.getPlanUserAuthority()));
		}
		user.setEnabled(true);
		user.setVerified(true);
		
		if(planUserService.getUser(user.getUsername()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(planUserService.savePlanUser(user));
	}
}
