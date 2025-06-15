package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Categories;
import com.ritesh.dineflow.repositories.CategoriesRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepository menuRepository;

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasAuthority('CREATE_MENU')")
	public void createMenuEntry(Categories menu) {
		if (menu.getId() != null) {
			updateMenuEntry(menu);
			return;
		}
		if (menuRepository.findByName(menu.getName()).isPresent()) {
			throw new ResourceAlreadyPresentException("Menu is Already Present");
		}
		menuRepository.save(menu);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasAuthority('UPDATE_MENU')")
	public void updateMenuEntry(Categories menu) {
		if (menu.getId() == null) {
			throw new ResourceNotFoundException("Menu is Not Present");
		}
		Categories previousMenu = menuRepository.findById(menu.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Menu is Not Present"));
		previousMenu.setName(menu.getName());
		previousMenu.setDescription(menu.getDescription());
		previousMenu.setActive(menu.isActive());
		previousMenu.setItems(menu.getItems());
		previousMenu.setRestaurantIds(menu.getRestaurantIds());
		menuRepository.save(previousMenu);
	}

	public Categories getMenuById(String id) {
		return menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Menu Found"));
	}

	public List<Categories> getMenusByRestaurant(String restaurantId) {
		return menuRepository.findByRestaurantIdsIn(restaurantId);
	}

	public List<Categories> getAllMenus() {
		if (SecurityUtils.isCurrentUserInRole("ROLE_SUPER_ADMIN")) {
			return menuRepository.findAll();
		}
		return menuRepository.findByCreatedBy(SecurityUtils.getCurrentUserId());
	}
}
