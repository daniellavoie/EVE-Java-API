package com.cspinformatique.eve.commons.service;

import java.util.List;

import com.cspinformatique.eve.commons.model.AccessMask;
import com.cspinformatique.eve.commons.model.Key;

public interface KeyService {
	public void deleteKey(String keyID);
	
	public Key getCharacterKey(String username, long characterId);
	
	public Key getKey(String keyID);
	
	public List<Key> getUserKeys(int userId);
	
	public boolean hasAccessTo(Key key, AccessMask accessMask);
	
	public void registerNewKey(Key key);
	
	public void saveKey(Key key);
}
