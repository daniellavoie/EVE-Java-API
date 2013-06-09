package com.cspinformatique.eve.commons.service;

import java.util.List;

import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.model.User;

public interface CharacterService {
	public Character getCharacter(long id, Key key);
	
	public List<Asset> getCharacterAssets(Character character, long regionID);
	
	public List<Character> getCharacters(Key key);
	
	public List<Character> getCharacters(User user);
	
	public List<Character> getCorporations(User user);
	
	public Character getDefaultCharacter(User user);
}
