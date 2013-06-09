package com.cspinformatique.eve.commons.model.market;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.model.Station;

@Entity
@Table(schema="jeve")
public class ItemResellStats {
	private int id;
	private Item item;
	private Station station;
	private double highestBuyBid;
	private double lowestSellBid;
	private Date lastRefresh;
	
	public ItemResellStats(){
		
	}
	
	public ItemResellStats(
		int id,
		Item item, 
		Station station, 
		double highestBuyBid,
		double lowestSellBid, 
		Date lastRefresh
	) {
		this.item = item;
		this.station = station;
		this.highestBuyBid = highestBuyBid;
		this.lowestSellBid = lowestSellBid;
		this.lastRefresh = lastRefresh;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "typeID", nullable = false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne
	@JoinColumn(name = "stationID", nullable = false)
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public double getHighestBuyBid() {
		return highestBuyBid;
	}

	public void setHighestBuyBid(double highestBuyBid) {
		this.highestBuyBid = highestBuyBid;
	}

	public double getLowestSellBid() {
		return lowestSellBid;
	}

	public void setLowestSellBid(double lowestSellBid) {
		this.lowestSellBid = lowestSellBid;
	}

	public Date getLastRefresh() {
		return lastRefresh;
	}

	public void setLastRefresh(Date lastRefresh) {
		this.lastRefresh = lastRefresh;
	}
}
