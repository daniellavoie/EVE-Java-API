package com.cspinformatique.eve.commons.model.market;

import java.util.Date;

import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.model.Station;

public class MarketOrder {
	private int quantity;
	private Item item;
	private Station station;
	private double bidPrice;
	private Date lastRefresh;
	
	public MarketOrder(){
		
	}
	
	public MarketOrder(
		int quantity, 
		Item item, 
		Station station, 
		double bidPrice, 
		Date lastRefresh
	) {
		this.quantity = quantity;
		this.item = item;
		this.station = station;
		this.bidPrice = bidPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Date getLastRefresh() {
		return lastRefresh;
	}

	public void setLastRefresh(Date lastRefresh) {
		this.lastRefresh = lastRefresh;
	}
}
