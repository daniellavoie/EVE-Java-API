package com.cspinformatique.eve.commons.service;

import java.util.List;

import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;

public interface AssetService {
	public double getAssetMarketValue(Asset asset, long regionID);
	
	public List<Asset> loadCharacterAssets(Character character, long regionID);
}
