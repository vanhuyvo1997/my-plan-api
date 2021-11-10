package com.myhope.myplan.service;

public enum PlanUserRoles {
	ADMIN("ADMIN"), USER("USER");

	private String role;

	private PlanUserRoles(String role) {
		this.role = role;
	}

	public String getPlanUserAuthority() {
		return role;
	}
}
