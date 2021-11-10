package com.myhope.myplan.service.authorityservice;

import java.util.List;

import com.myhope.myplan.domain.PlanUserRole;
import com.myhope.myplan.service.PlanUserRoles;

public interface PlanAuthorityService {
	public List<PlanUserRole> planUserRoles();

	public PlanUserRole savePlanUserRole(PlanUserRoles planUserRoles);
}
