package com.ritesh.dineflow.services.treeHandler;

import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.treeHandler.Story;
import com.ritesh.dineflow.repositories.treeHandler.StoryRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {

	@Autowired
	private StoryRepository storyRepository;

	public Story getStoryById(String id) {
		return storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Story Found"));
	}

	public List<Story> getAllStory() {
		return storyRepository.findAll();
	}

	public List<Story> getStoriesByOwner(String id) {
		return storyRepository.findByOwner(id);
	}

	public void saveStory(Story story) {
		String currentUserId = SecurityUtils.getCurrentUserId();
		story.setOwner(currentUserId);
		storyRepository.save(story);
	}
}
