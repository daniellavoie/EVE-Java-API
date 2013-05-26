package com.cspinformatique.eve.commons.service;

import java.util.List;

import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;

public interface CharacterService {
	public Character getCharacter(long id, String keyID, String vCode);
	
	public List<Asset> getCharacterAssets(Character character, long regionID);
	
	public List<Character> getCharacters(String keyID, String vCode);
}
