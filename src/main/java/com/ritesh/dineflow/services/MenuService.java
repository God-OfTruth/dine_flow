package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.exceptions.UnauthorizedAccessException;
import com.ritesh.dineflow.models.Menu;
import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.repositories.MenuRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private RestaurantService restaurantService;

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasAuthority('CREATE_MENU')")
	public void createMenuEntry(Menu menu, String restaurantId) {
		if (menu.getId() != null) {
			throw new ResourceAlreadyPresentException("Menu is Already Present");
		}
		if (menuRepository.findByName(menu.getName()).isPresent()) {
			throw new ResourceAlreadyPresentException("Menu is Already Present");
		}
		Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);
		String currentUserId = SecurityUtils.getCurrentUserId();
		if (!restaurant.getOwnerId().equals(currentUserId)) {
			throw new UnauthorizedAccessException("Contact Owner");
		}
		Menu createdManu = menuRepository.save(menu);
		restaurant.getMenuIds().add(createdManu.getId());
		restaurantService.updateRestaurantEntry(restaurant);

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
		previousMenu.setItems(menu.getItems());
		menuRepository.save(previousMenu);
	}

	public Menu getMenuById(String id) {
		return menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Menu Found"));
	}

	public List<Menu> getMenusByRestaurant(String restaurantId) {
		return menuRepository.findByRestaurantIdsIn(restaurantId);
	}

	public List<Menu> getAllMenus() {
		return menuRepository.findAll();
	}
}
