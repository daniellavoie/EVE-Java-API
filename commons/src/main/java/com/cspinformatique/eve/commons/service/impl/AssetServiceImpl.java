package com.cspinformatique.eve.commons.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cspinformatique.eve.commons.connector.EVECharacterConnector;
import com.cspinformatique.eve.commons.connector.EVEConnector;
import com.cspinformatique.eve.commons.connector.MarketConnector;
import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.model.market.MarketStats;
import com.cspinformatique.eve.commons.service.AssetService;
import com.cspinformatique.eve.commons.service.ItemService;
import com.cspinformatique.eve.commons.service.LocationService;

@Service
public class AssetServiceImpl implements AssetService {
	public static final String ASSETLIST_URL = "/char/AssetList.xml.aspx";
	
	@Autowired private EVECharacterConnector eveCharacterConnector;
	@Autowired private MarketConnector marketConnector;
	
	@Autowired private ItemService itemService;
	@Autowired private LocationService locationService;
	
	@Override
	public double getAssetMarketValue(Asset asset, long regionID){
		return this.itemService.getItemMarketValue(
			asset.getItem().getTypeID(), 
			regionID
		) * asset.getQuantity();
	}
	
	@Override
	public List<Asset> loadCharacterAssets(Character character, long regionID){
		// Calling web service for info.
		List<String> params = new ArrayList<String>();
		params.add("characterID=" + character.getEveId());
		
		// Retreiving character asset raw data from EVE API.
		Document assetsDocument =	eveCharacterConnector.executeRequest(
										character, 
										ASSETLIST_URL, 
										params
									);
		
		// Parsing XML result from EVE API.
		List<Asset> assets = new ArrayList<Asset>();
		NodeList assetsNodeList =	EVEConnector.getNodeList(
										"/eveapi/result/rowset/row", 
										assetsDocument
									);
		
		for(int i = 0; i < assetsNodeList.getLength(); i++){
			Node node = assetsNodeList.item(i);
			if(node instanceof Element){
				Element assetElement = (Element)node;
				
				Item item =	this.itemService.getItem(
								Long.parseLong(assetElement.getAttribute("typeID"))
							);
				
				if(item.getTradable()){
					assets.add(
						new Asset(
							item, 
							Long.parseLong(assetElement.getAttribute("quantity")), 
							this.locationService.getLocation(
								Long.parseLong(assetElement.getAttribute("locationID"))
							),
							null
						)
					);
				}
			}
		}
		
		// Building the list of id to query.
		List<Long> typeIDs = new ArrayList<Long>();
		for(Asset asset : assets){
			typeIDs.add(asset.getItem().getTypeID());
		}
		
		// Quering EVE Market with id list.
		Map<Long, MarketStats> marketStats =	this.marketConnector.getMarketStats(
													typeIDs, regionID
												);
		
		for(Asset asset : assets){
			asset.setMarketStats(marketStats.get(asset.getItem().getTypeID()));
		}
		
		return assets;
	}
}
