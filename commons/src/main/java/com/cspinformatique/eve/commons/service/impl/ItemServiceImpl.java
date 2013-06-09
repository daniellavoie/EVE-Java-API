package com.cspinformatique.eve.commons.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.eve.commons.connector.MarketConnector;
import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.model.market.MarketStats;
import com.cspinformatique.eve.commons.repository.eve.ItemRepository;
import com.cspinformatique.eve.commons.service.ItemService;
import com.cspinformatique.eve.commons.service.RegionService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired private ItemRepository itemRepository;
	@Autowired private MarketConnector marketConnector;
	@Autowired private RegionService regionService;
	
	@Override
	public Item getItem(long id) {
		return this.itemRepository.findOne(id);
	}
	
	@Override
	public MarketStats getItemMarketStats(long typeID, long regionID){
		return this.marketConnector.getMarketStats(typeID, regionID);
	}
	
	@Override
	public double getItemMarketValue(long typeID, long regionID){
		return this.getItemMarketStats(typeID, regionID).getAllStats().getAvg();
	}
	
	@Override
	public List<Item> getTradableItems(){
		return this.itemRepository.findTradableItems();
	}
	
	@Override
	public List<Asset> findBestTradableItems(long regionID){
		List<Asset> assets = new ArrayList<Asset>();
		
		List<Item> items = this.getTradableItems();
		List<Long> itemIDs = new ArrayList<Long>();
		for(Item item : items){
			itemIDs.add(item.getTypeID());
			
			assets.add(
				new Asset(
					item, 
					0, 
					null, 
					null,
					new ArrayList<Asset>()
				)
			);
		}
		
		Map<Long, MarketStats> marketStatsMap =	this.marketConnector.getMarketStats(
												itemIDs, 
													regionID
												);
		
		for(Asset asset : assets){
			asset.setMarketStats(marketStatsMap.get(asset.getItem().getTypeID()));
		}
		
		Collections.sort(
			assets, 
			new Comparator<Asset>() {
				@Override
				public int compare(Asset o1, Asset o2) {
					if(	o1.getMarketStats() == null || 
						o1.getMarketStats().getAllStats().getVolume() < 
							o2.getMarketStats().getAllStats().getVolume()
					){
						return 1;
					}else if(	o2.getMarketStats() == null ||
								o1.getMarketStats().getAllStats().getVolume() > 
									o2.getMarketStats().getAllStats().getVolume()
					){
						return -1;
					}else{
						return 0;
					}
				}
			}
		);
		
		// Checking amongs the most 50 traded products.
		List<Asset> bestAssets = new ArrayList<Asset>();
		Asset bestAsset = null;
		for(int i = 0; i < 50; i++){
			Asset asset = assets.get(i);
			bestAssets.add(asset);
			
			System.out.println(
				"Checking asset : " + 
					asset.getItem().getName() + 
				" - Volume : " + 
					asset.getMarketStats().getAllStats().getVolume() + 
				" - Ratio : " + asset.getMarketStats().getResellRatio()
			);
			
			if(	bestAsset == null || 
					asset.getMarketStats().getResellRatio() > 
						bestAsset.getMarketStats().getResellRatio()
			){
				bestAsset = asset;
			}
		}
		
		System.out.println(
			"Best asset found - " + 
				bestAsset.getItem().getName() + 
			" - Volume : " + 
				bestAsset.getMarketStats().getAllStats().getVolume() + " - " +
			"Ratio : " + bestAsset.getMarketStats().getResellRatio()
		);
		
		return bestAssets;
	}
}
