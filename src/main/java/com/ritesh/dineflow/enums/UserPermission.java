package com.ritesh.dineflow.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserPermission implements GrantedAuthority {
	CREATE_ACCOUNT,
	DELETE_ACCOUNT,
	UPDATE_ACCOUNT,
	VIEW_ACCOUNT;

	@Override
	public String getAuthority() {
		return name();
	}
}
