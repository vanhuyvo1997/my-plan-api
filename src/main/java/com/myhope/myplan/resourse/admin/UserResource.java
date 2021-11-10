package com.myhope.myplan.resourse.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhope.myplan.domain.PlanUser;
import com.myhope.myplan.service.userservice.PlanUserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/management/user")
@AllArgsConstructor
public class UserResource {

	private final PlanUserService planUserService;

	@GetMapping
	public ResponseEntity<?> getPlanUsers() {
		return ResponseEntity.ok(planUserService.getUsers());
	}

	@GetMapping("{userId}")
	public ResponseEntity<?> getPlanUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(planUserService.getUser(userId));
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody PlanUser user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(planUserService.savePlanUser(user));
	}

	@PutMapping()
	public ResponseEntity<?> updateUser(@RequestBody PlanUser user) {
		return ResponseEntity.status(HttpStatus.OK).body(planUserService.savePlanUser(user));
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Integer userId) {
		planUserService.deletePlanUserById(userId);
	}
}
