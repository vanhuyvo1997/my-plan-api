package com.myhope.myplan.service.userservice;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myhope.myplan.domain.PlanUser;
import com.myhope.myplan.domain.PlanUserRole;
import com.myhope.myplan.repository.PlanUserRepo;
import com.myhope.myplan.repository.PlanUserRoleRepo;
import com.myhope.myplan.service.PlanUserRoles;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PlanUserSeviceImpl implements PlanUserService {

	private final PlanUserRepo planUserRepo;
	private final PlanUserRoleRepo planUserRoleRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void deletePlanUser(PlanUser planUser) {
		log.info("Deleting user has id {}", planUser.getId());
		planUserRepo.delete(planUser);
	}

	@Override
	public Iterable<PlanUser> getUsers(int start, int size) {
		log.info("Fetching user from {} to {}", start, size);
		Pageable pageable = PageRequest.of(start, size);
		return planUserRepo.findAll(pageable);
	}

	@Override
	public void addRoleToUser(String userName, PlanUserRoles planUserRoles) {
		log.info("Adding role: {} to user has id: {}", planUserRoles.name(), userName);
		PlanUser planUser = planUserRepo.findPlanUserByUsername(userName);
		PlanUserRole planUserRole = planUserRoleRepo.findByName(planUserRoles.name());
		planUser.getPlanUserRoles().add(planUserRole);
	}

	@Override
	public PlanUser savePlanUser(PlanUser planUser) {
		log.info("Saving user has userName: {}", planUser.getUsername());
		planUser.setPassword(passwordEncoder.encode(planUser.getPassword()));
		return planUserRepo.save(planUser);
	}

	@Override
	public PlanUser getUser(String username) {
		log.info("Getting use has usename: {}", username);
		return planUserRepo.findPlanUserByUsername(username);
	}

	@Override
	public Iterable<PlanUser> getUsers() {
		log.info("Fetching all user");
		return planUserRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loading user: {}", username);
		PlanUser planUser = planUserRepo.findPlanUserByUsername(username);
		if(planUser == null) {
			log.warn("User {} can not be found", username);
			throw new UsernameNotFoundException(String.format("User %s can not be found", username));
		}
		return User.builder()
					.username(planUser.getUsername())
					.password(planUser.getPassword())
					.disabled(!planUser.isEnabled())
					.roles(planUser.getPlanUserRoles()
										.stream()
										.map(PlanUserRole::getName).toArray(String[]::new))
					.build();
	}

	@Override
	public void deletePlanUserById(Integer userId) {
		planUserRepo.deleteById(userId.longValue());
	}

	@Override
	public PlanUser getUser(Integer userId) {
		return planUserRepo.getOne(userId.longValue());
	}

}
