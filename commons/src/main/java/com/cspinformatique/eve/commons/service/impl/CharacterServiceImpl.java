package com.cspinformatique.eve.commons.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cspinformatique.eve.commons.connector.EVEConnector;
import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.model.User;
import com.cspinformatique.eve.commons.service.AssetService;
import com.cspinformatique.eve.commons.service.CharacterService;
import com.cspinformatique.eve.commons.service.KeyService;

@Service
public class CharacterServiceImpl implements CharacterService{ 
	private static final String CHARACTERS_RESOURCE =
			"/account/Characters.xml.aspx";
	
	@Autowired private AssetService assetService;
	@Autowired private KeyService keyService;
	
	@Autowired private EVEConnector eveCharacterConnector;
	
	@Override
	public Character getCharacter(long id, Key key){
		Character result = null;
		for(Character character : this.getCharacters(key)){
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
	public List<Character> getCharacters(Key key){
		Document charactersDocument = this.eveCharacterConnector.executeRequest(key, CHARACTERS_RESOURCE);
		
		NodeList charactersNodeList =	EVEConnector.getNodeList(
											"/eveapi/result/rowset/row", 
											charactersDocument
										);
		
		List<Character> characters = new ArrayList<Character>();
		for(int i = 0; i < charactersNodeList.getLength(); i++){
			Node node = charactersNodeList.item(i);
			if(node instanceof Element){
				Element characterElement = (Element)node;
				
				characters.add(
					new Character(
						Long.parseLong(characterElement.getAttribute("characterID")),
						key,
						characterElement.getAttribute("name"),
						Long.parseLong(characterElement.getAttribute("corporationID"))
					)
				);
			}
		}
		
		return characters;
	}
	
	@Override 
	public List<Character> getCharacters(User user){
		List<Character> characters = new ArrayList<Character>();
		for(Key key : this.keyService.getUserKeys(user.getId())){
			if(key.getType().equals(Key.TYPE_CHARACTER)){
				for(Character character : this.getCharacters(key)){
					characters.add(character);
				}
			}
		}
		return characters;
	}
	
	@Override 
	public List<Character> getCorporations(User user){
		List<Character> characters = new ArrayList<Character>();
		for(Key key : this.keyService.getUserKeys(user.getId())){
			if(key.getType().equals(Key.TYPE_CORPORATION)){
				for(Character character : this.getCharacters(key)){
					characters.add(character);
				}
			}
		}
		return characters;
	}
	
	@Override
	public Character getDefaultCharacter(User user){
		return this.getCharacters(user).get(0);
	}
}
