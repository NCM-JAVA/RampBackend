package com.ramp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramp.entity.RoleEntity;
import com.ramp.enums.RoleType;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	
    RoleEntity findByRoleName(RoleType roleType);
}
