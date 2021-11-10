package com.myhope.myplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myhope.myplan.domain.PlanUserRole;

public interface PlanUserRoleRepo extends JpaRepository<PlanUserRole, Integer>{
	PlanUserRole findByName(String name);
}
