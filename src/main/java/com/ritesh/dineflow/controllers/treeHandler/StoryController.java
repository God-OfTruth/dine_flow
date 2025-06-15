package com.ritesh.dineflow.controllers.treeHandler;

import com.ritesh.dineflow.models.treeHandler.Story;
import com.ritesh.dineflow.services.treeHandler.StoryService;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tree-handler/story")
public class StoryController {

	@Autowired
	private StoryService storyService;


	@PostMapping()
	public ResponseEntity<Void> saveStory(@RequestBody Story story){
		storyService.saveStory(story);
		return ResponseEntity.ok().build();
	}

	@GetMapping()
	public ResponseEntity<List<Story>> getAllStories(){
		String currentUserId = SecurityUtils.getCurrentUserId();
		if (SecurityUtils.isCurrentUserInRole("ROLE_SUPER_ADMIN")) {
			return ResponseEntity.ok().body(storyService.getAllStory());
		}
		return ResponseEntity.ok().body(storyService.getStoriesByOwner(currentUserId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Story> getStory(@PathVariable String id){
		return ResponseEntity.ok().body(storyService.getStoryById(id));
	}
}
