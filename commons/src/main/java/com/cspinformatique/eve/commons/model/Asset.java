package com.cspinformatique.eve.commons.model;

import com.cspinformatique.eve.commons.model.market.MarketStats;

public class Asset {
	private Item item;
	private long quantity;
	private Location location;
	private MarketStats marketStats;
	
	public Asset(Item item, long quantity, Location location, MarketStats marketStats) {
		this.item = item;
		this.quantity = quantity;
		this.location = location;
		this.marketStats = marketStats;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public MarketStats getMarketStats() {
		return marketStats;
	}

	public void setMarketStats(MarketStats marketStats) {
		this.marketStats = marketStats;
	}
}
