package com.cspinformatique.eve.commons.service;

import com.cspinformatique.eve.commons.model.Item;

public interface ItemService {
	public Item getItem(long id);
	
	public double getItemMarketValue(long typeID, long regionID);
}
