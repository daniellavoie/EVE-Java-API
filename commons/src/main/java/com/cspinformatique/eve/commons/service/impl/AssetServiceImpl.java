package com.cspinformatique.eve.commons.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cspinformatique.eve.commons.connector.EVEConnector;
import com.cspinformatique.eve.commons.connector.MarketConnector;
import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Asset.Owner;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.model.Station;
import com.cspinformatique.eve.commons.model.market.MarketStats;
import com.cspinformatique.eve.commons.service.AssetService;
import com.cspinformatique.eve.commons.service.ItemService;
import com.cspinformatique.eve.commons.service.StationService;

@Service
public class AssetServiceImpl implements AssetService {
	public static final String CHARACTER_URL = "/char/AssetList.xml.aspx";
	public static final String CORPORATION_URL = "/corp/AssetList.xml.aspx";
	
	@Autowired private EVEConnector eveConnector;
	@Autowired private MarketConnector marketConnector;
	
	@Autowired private ItemService itemService;
	@Autowired private StationService stationService;
	
	@Override
	public double getAssetMarketValue(Asset asset, long regionID){
		return this.itemService.getItemMarketValue(
			asset.getItem().getTypeID(), 
			regionID
		) * asset.getQuantity();
	}
	
	private List<Asset> loadAssets(Character character, long regionID, Owner owner){
		// Calling web service for info.
		List<String> params = new ArrayList<String>();
		params.add("characterID=" + character.getEveId());
		
		// Retreiving character asset raw data from EVE API.
		String assetUrl = null;
		if(owner == Owner.CHARACTER){
			assetUrl = CHARACTER_URL;
		}else{
			assetUrl = CORPORATION_URL;
		}
		
		Document assetsDocument =	this.eveConnector.executeRequest(
										character.getKey(), 
										assetUrl, 
										params
									);
		
		// Parsing XML result from EVE API.
		Node assetsNode =	EVEConnector.getNode(
										"/eveapi/result/rowset", 
										assetsDocument
									);
		
		return this.loadAssetsFromDomNode(assetsNode, regionID);
	}
	
	private List<Asset> loadAssetsFromDomNode(Node node, long regionID){
		return this.loadAssetsFromDomNode(node, null, regionID);
	}
	
	private List<Asset> loadAssetsFromDomNode(Node node, Station parentStation, long regionID){
		List<Asset> assets = new ArrayList<Asset>();
		
		for(int i = 0; i < node.getChildNodes().getLength(); i++){
			Node childNode = node.getChildNodes().item(i);
			if(childNode instanceof Element){
				Element assetElement = (Element)childNode;
				
				Item item =	this.itemService.getItem(
								Long.parseLong(assetElement.getAttribute("typeID"))
							);
				
				Station station = parentStation;
				
				if(station == null){
					long stationID = Long.parseLong(assetElement.getAttribute("locationID"));
					if(Long.parseLong(assetElement.getAttribute("typeID")) == 27){
						stationID = stationID - 6000001;
					}
					
					station = this.stationService.getStation(stationID);
				}
				
				List<Asset> assetContent = new ArrayList<Asset>();
				
				if(item.hasInventory()){
					Node nestedNode = EVEConnector.getNode("rowset", childNode);
					if(nestedNode != null){
						assetContent = this.loadAssetsFromDomNode(nestedNode, station, regionID);
					}
				}
				
				if(item.tradable() || item.hasInventory()){
					assets.add(
						new Asset(
							item, 
							Long.parseLong(assetElement.getAttribute("quantity")), 
							station,
							null,
							assetContent
						)
					);
				}
			}
		}
		
		// Building the list of id to query.
		List<Long> typeIDs = new ArrayList<Long>();
		for(Asset asset : assets){
			if(asset.getItem().tradable()){
				typeIDs.add(asset.getItem().getTypeID());
			}
		}
		
		// Quering EVE Market with id list.
		Map<Long, MarketStats> marketStats =	this.marketConnector.getMarketStats(
													typeIDs, regionID
												);
		
		for(Asset asset : assets){
			if(asset.getItem().tradable()){
				asset.setMarketStats(marketStats.get(asset.getItem().getTypeID()));
			}
		}
		
		return assets;
	}
	
	public List<Asset> loadCharacterAssets(Character character, long regionID){
		return this.loadAssets(character, regionID, Owner.CHARACTER);
	}
	
	public List<Asset> loadCorporationAssets(Character character, long regionID){
		return this.loadAssets(character, regionID, Owner.CORPORATION);
	}
}
