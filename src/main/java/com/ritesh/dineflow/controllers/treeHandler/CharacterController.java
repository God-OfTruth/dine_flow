package com.ritesh.dineflow.controllers.treeHandler;

import com.ritesh.dineflow.models.treeHandler.Character;
import com.ritesh.dineflow.services.treeHandler.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tree-handler/character")
public class CharacterController {

	@Autowired
	private CharacterService characterService;

	@PostMapping()
	public ResponseEntity<Void> saveStory(@RequestBody Character story) {
		characterService.saveCharacter(story);
		return ResponseEntity.ok().build();
	}

	@GetMapping()
	public ResponseEntity<List<Character>> getCharacterByStory(@RequestParam("ids") List<String> ids) {
		if(ids != null && !ids.isEmpty()){
			return ResponseEntity.ok(characterService.getCharacters(ids));
		}
		return ResponseEntity.ok().body(characterService.getAllCharacter());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Character> getCharacterById(@PathVariable String id) {
		return ResponseEntity.ok(characterService.getCharacterById(id));
	}
}
