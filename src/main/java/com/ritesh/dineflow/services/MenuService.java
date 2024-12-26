package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Menu;
import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.repositories.MenuRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

//	@Autowired
//	private RestaurantService restaurantService;

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasAuthority('CREATE_MENU')")
	public void createMenuEntry(Menu menu) {
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
		previousMenu.setRestaurantIds(menu.getRestaurantIds());
		menuRepository.save(previousMenu);
	}

	public Menu getMenuById(String id) {
		return menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Menu Found"));
	}

	public List<Menu> getMenusByRestaurant(String restaurantId) {
		return menuRepository.findByRestaurantIdsIn(restaurantId);
	}

	public List<Menu> getAllMenus() {
		User user = SecurityUtils.getCurrentUser();
//		List<Restaurant> restaurantIds = restaurantService.findByOwnerId(user.getId());

		return menuRepository.findAll();
	}
}
