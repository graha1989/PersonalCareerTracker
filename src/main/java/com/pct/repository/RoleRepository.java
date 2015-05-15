package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * Finds role by role name.
	 * 
	 * @param name
	 * @return Role
	 */
	Role findByName(String name);
}
