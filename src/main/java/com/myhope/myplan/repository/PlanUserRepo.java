package com.myhope.myplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myhope.myplan.domain.PlanUser;

public interface PlanUserRepo extends JpaRepository<PlanUser, Long>{
	public PlanUser findPlanUserByUsername(String username);
}
