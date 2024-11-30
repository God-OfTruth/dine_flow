package com.ritesh.dineflow.configuration.seeders;

import com.ritesh.dineflow.enums.UserPermission;
import com.ritesh.dineflow.enums.UserRole;
import com.ritesh.dineflow.models.Permission;
import com.ritesh.dineflow.models.Role;
import com.ritesh.dineflow.repositories.PermissionRepository;
import com.ritesh.dineflow.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author riteshkumar
 * @apiNote This is used to create Default Super Admin Account.
 */
@Component
@Order(1)
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.loadRoles();
	}

	private void loadRoles() {
		List<UserRole> roles = List.of(UserRole.SUPER_ADMIN, UserRole.ADMIN,UserRole.MANAGER, UserRole.USER, UserRole.PUBLIC);
		Map<UserRole, String> roleDescriptionMap = Map.of(
				UserRole.PUBLIC, "Public Role",
				UserRole.USER, "Default user role",
				UserRole.ADMIN, "Administrator role",
				UserRole.MANAGER, "Manger Role",
				UserRole.SUPER_ADMIN, "Super Administrator role"
		);
		List<UserPermission> permissions = List.of(UserPermission.VIEW_ACCOUNT, UserPermission.CREATE_ACCOUNT, UserPermission.DELETE_ACCOUNT);

		permissions.forEach(permission -> {
			Optional<Permission> optionalPermission = permissionRepository.findByPermission(permission);
			optionalPermission.ifPresentOrElse(System.out::println, () -> permissionRepository.save(Permission.builder()
					.permission(permission)
					.build()));
		});

		Map<UserRole, Set<Permission>> rolePermissionMap = Map.of(
				UserRole.SUPER_ADMIN, Set.of(
						Objects.requireNonNull(permissionRepository.findByPermission(UserPermission.VIEW_ACCOUNT).orElse(null)),
						Objects.requireNonNull(permissionRepository.findByPermission(UserPermission.CREATE_ACCOUNT).orElse(null)),
						Objects.requireNonNull(permissionRepository.findByPermission(UserPermission.DELETE_ACCOUNT).orElse(null))),
				UserRole.ADMIN, Set.of(
						Objects.requireNonNull(permissionRepository.findByPermission(UserPermission.CREATE_ACCOUNT).orElse(null)),
						Objects.requireNonNull(permissionRepository.findByPermission(UserPermission.VIEW_ACCOUNT).orElse(null))
				),
				UserRole.USER, Set.of(
						Objects.requireNonNull(permissionRepository.findByPermission(UserPermission.VIEW_ACCOUNT).orElse(null))
				)
		);
		roles.forEach((roleName) -> {
			Optional<Role> optionalRole = roleRepository.findByRole(roleName);

			optionalRole.ifPresentOrElse(System.out::println, () -> {
				roleRepository.save(Role.builder()
						.role(roleName)
						.description(roleDescriptionMap.get(roleName))
						.permissions(rolePermissionMap.get(roleName))
						.build());
			});
		});
	}
}
