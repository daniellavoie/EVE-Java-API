package com.cspinformatique.eve.commons.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.eve.commons.connector.MarketConnector;
import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.repository.ItemRepository;
import com.cspinformatique.eve.commons.service.ItemService;
import com.cspinformatique.eve.commons.service.RegionService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired private ItemRepository itemRepository;
	@Autowired private MarketConnector marketConnector;
	@Autowired private RegionService regionService;
	
	@Override
	public Item getItem(long id) {
		return this.itemRepository.getItem(id);
	}
	
	@Override
	public double getItemMarketValue(long typeID, long regionID){
		
		return this.marketConnector.getMarketStats(typeID, regionID).getAllStats().getAvg();
	}
}
