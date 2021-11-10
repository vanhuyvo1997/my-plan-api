package com.myhope.myplan.service.userservice;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.myhope.myplan.domain.PlanUser;
import com.myhope.myplan.service.PlanUserRoles;

public interface PlanUserService extends UserDetailsService{
	public PlanUser savePlanUser(PlanUser planUser);

	public void deletePlanUser(PlanUser planUser);

	public PlanUser getUser(String username);

	public Iterable<PlanUser> getUsers(int start, int size);

	public Iterable<PlanUser> getUsers();

	public void addRoleToUser(String userName, PlanUserRoles planUserRoles);

	public void deletePlanUserById(Integer userId);

	public PlanUser getUser(Integer userId);
}
