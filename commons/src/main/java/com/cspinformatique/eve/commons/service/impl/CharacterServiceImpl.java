package com.cspinformatique.eve.commons.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.eve.commons.connector.EVECharacterConnector;
import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.service.AssetService;
import com.cspinformatique.eve.commons.service.CharacterService;

@Service
public class CharacterServiceImpl implements CharacterService{ 
	@Autowired private AssetService assetService;
	
	@Autowired
	private EVECharacterConnector eveCharacterConnector;
	
	@Override
	public Character getCharacter(long id, String keyID, String vCode){
		Character result = null;
		for(Character character : this.getCharacters(keyID, vCode)){
			if(character.getEveId() == id){
				result = character;
				break;
			}
		}
		
		return result;
	}
	
	@Override
	public List<Asset> getCharacterAssets(Character character, long regionID){
		return this.assetService.loadCharacterAssets(character, regionID);
	}
	
	@Override 
	public List<Character> getCharacters(String keyID, String vCode){
		return this.eveCharacterConnector.getCharacters(keyID, vCode);
	}
}
