package com.ramp.entity;

import javax.persistence.*;

import com.ramp.enums.RoleType;


	
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Enumerated(EnumType.STRING) // Store enum values as strings in DB
    @Column(nullable = false, unique = true)
    private RoleType roleName;

    private String rolePermission;

    public RoleEntity() {}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public RoleType getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleType roleName) {
		this.roleName = roleName;
	}

	public String getRolePermission() {
		return rolePermission;
	}

	public void setRolePermission(String rolePermission) {
		this.rolePermission = rolePermission;
	}

	@Override
	public String toString() {
		return "RoleEntity [roleId=" + roleId + ", roleName=" + roleName + ", rolePermission=" + rolePermission + "]";
	}

    
}
