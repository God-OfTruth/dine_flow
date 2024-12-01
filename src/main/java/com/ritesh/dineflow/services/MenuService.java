package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Menu;
import com.ritesh.dineflow.repositories.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

	@Autowired
	private MenuRepository menuRepository;

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasAuthority('CREATE_MENU')")
	public void createMenuEntry(Menu menu) {
		if (menu.getId() != null) {
			throw new ResourceAlreadyPresentException("Menu is Already Present");
		}
		menuRepository.save(menu);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasAuthority('UPDATE_MENU')")
	public void updateMenuEntry(Menu menu) {
		if (menu.getId() == null) {
			throw new ResourceNotFoundException("Menu is Not Present");
		}
		Menu previousMenu = menuRepository.findById(menu.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Menu is Not Present"));
		previousMenu.setName(menu.getName());
		previousMenu.setDescription(menu.getDescription());
		previousMenu.setActive(menu.isActive());
		menuRepository.save(menu);
	}

}
