package com.myhope.myplan.service.authorityservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myhope.myplan.domain.PlanUserRole;
import com.myhope.myplan.repository.PlanUserRoleRepo;
import com.myhope.myplan.service.PlanUserRoles;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PlanAuthorityServiceImpl implements PlanAuthorityService {
	
	private final PlanUserRoleRepo planUserRoleRepo;

	@Override
	public List<PlanUserRole> planUserRoles() {
		log.info("Fetching all PlanUserRoles");
		return planUserRoleRepo.findAll();
	}
	
	public PlanUserRole savePlanUserRole(PlanUserRoles planUserRoles) {
		return planUserRoleRepo.save(new PlanUserRole(null, planUserRoles.name()));
	}

}
