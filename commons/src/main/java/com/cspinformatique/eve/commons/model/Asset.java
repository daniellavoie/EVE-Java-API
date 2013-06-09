package com.cspinformatique.eve.commons.model;

import java.util.List;

import com.cspinformatique.eve.commons.model.market.MarketStats;

public class Asset {	
	public enum Owner {
		CHARACTER,
		CORPORATION
	}
	
	private Item item;
	private long quantity;
	private Station station;
	private MarketStats marketStats;
	private List<Asset> assetContent;
	
	public Asset(Item item, long quantity, Station station, MarketStats marketStats, List<Asset> assetContent) {
		this.item = item;
		this.quantity = quantity;
		this.station = station;
		this.marketStats = marketStats;
		this.assetContent = assetContent;
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

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public MarketStats getMarketStats() {
		return marketStats;
	}

	public void setMarketStats(MarketStats marketStats) {
		this.marketStats = marketStats;
	}

	public List<Asset> getAssetContent() {
		return assetContent;
	}

	public void setAssetContent(List<Asset> assetContent) {
		this.assetContent = assetContent;
	}
}
