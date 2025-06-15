package com.ritesh.dineflow.services.treeHandler;

import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.treeHandler.Character;
import com.ritesh.dineflow.repositories.treeHandler.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

	@Autowired
	private CharacterRepository characterRepository;

	public Character getCharacterById(String id){
		return characterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Story Found"));
	}

	public List<Character> getAllCharacter(){
		return characterRepository.findAll();
	}

	public List<Character> getCharacters(List<String> ids){
		return characterRepository.findAllById(ids);
	}
	public void saveCharacter(Character story){
		characterRepository.save(story);
	}
}
