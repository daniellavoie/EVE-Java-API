package com.cspinformatique.eve.commons.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.cspinformatique.eve.commons.connector.EVEConnector;
import com.cspinformatique.eve.commons.exception.KeyAlreadyExistsException;
import com.cspinformatique.eve.commons.model.AccessMask;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.repository.eve.KeyRepository;
import com.cspinformatique.eve.commons.service.CharacterService;
import com.cspinformatique.eve.commons.service.KeyService;
import com.cspinformatique.eve.commons.service.UserService;

@Service
public class KeyServiceImpl implements KeyService {
	private static final String API_MASK_RESOURCE = 
		"/account/APIKeyInfo.xml.aspx";
	
	
	@Autowired private CharacterService characterService;
	@Autowired private EVEConnector eveConnector;
	@Autowired private KeyRepository keyRepository;
	@Autowired private UserService userService;
	
	@Override
	public void deleteKey(String keyID){
		this.keyRepository.delete(keyID);
	}
	
	@Override
	public Key getCharacterKey(String username, long characterId){
		for(Key key : this.getUserKeys(this.userService.getUser(username).getId())){
			for(Character character : this.characterService.getCharacters(key)){
				if(characterId == character.getEveId()){
					return key;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public Key getKey(String keyID){
		Key key = this.keyRepository.findOne(keyID);
		
		if(key != null){
			this.loadKeyAccessMask(key);
		}
		
		return key;
	}
	
	@Override
	public List<Key> getUserKeys(int userId) {
		List<Key> keys = this.keyRepository.findByUserId(userId);
		
		for(Key key : keys){
			this.loadKeyAccessMask(key);
		}
		return keys;
	}
	
	@Override
	public boolean hasAccessTo(Key key, AccessMask accessMask){
		String binaryValue = Long.toBinaryString(key.getAccessMask());
		
		if(	(	key.getType().equals(Key.TYPE_CHARACTER) && 
				accessMask.name().startsWith("Character")
			) || (
				key.getType().equals(Key.TYPE_CORPORATION) &&
				accessMask.name().startsWith("Corporation")
			) && 
			binaryValue.length() >= accessMask.getBit() && 
			String.valueOf(binaryValue.charAt(binaryValue.length() - accessMask.getBit())).equals("1")
		){
			return true;
		}
		
		return false;
	}
	
	@Override
	public void registerNewKey(Key key){
		if(this.getKey(key.getKeyID()) != null){
			throw new KeyAlreadyExistsException("Key already exists.");
		}
		
		this.saveKey(key);
	}

	@Override
	public void saveKey(Key key){		
		this.keyRepository.saveAndFlush(key);
	}
	
	private void loadKeyAccessMask(Key key){
		Document maskDocument = this.eveConnector.executeRequest(key, API_MASK_RESOURCE);
		
		key.setAccessMask(
			Long.parseLong(
				EVEConnector.extractField("/eveapi/result/key/@accessMask", maskDocument)
			)
		);
		
		key.setType(
			EVEConnector.extractField("/eveapi/result/key/@type", maskDocument)
		);
		
		List<AccessMask> grants = null;
		if(key.getType().equals(Key.TYPE_CHARACTER)){
			grants = AccessMask.characterValues();
		}else{
			grants = AccessMask.corporationValues();
		}
		
		key.setGrants(new ArrayList<AccessMask>());
		for(AccessMask accessMask : grants){
			if(this.hasAccessTo(key, accessMask)){
				key.getGrants().add(accessMask);
			}
		}
	}
}
