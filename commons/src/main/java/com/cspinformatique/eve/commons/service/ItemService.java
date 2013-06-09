package com.cspinformatique.eve.commons.service;

import java.util.List;

import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.model.market.MarketStats;

public interface ItemService {
	public List<Asset> findBestTradableItems(long regionID);
	
	public Item getItem(long id);
	
	public MarketStats getItemMarketStats(long typeID, long regionID);
	
	public double getItemMarketValue(long typeID, long regionID);
	
	public List<Item> getTradableItems();
}
