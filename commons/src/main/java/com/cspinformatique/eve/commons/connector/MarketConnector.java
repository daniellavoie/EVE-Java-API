package com.cspinformatique.eve.commons.connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.cspinformatique.eve.commons.model.market.MarketStats;
import com.cspinformatique.eve.commons.model.market.Stat;
import com.cspinformatique.eve.commons.model.market.Stat.Types;

@Component
public class MarketConnector extends RestConnector {
	private static final String API_URL = "http://api.eve-central.com";
	private static final String RESOURCE_MARKETSTATS = "/api/marketstat";
	
	public MarketStats getMarketStats(long typeID, long regionID){
		List<Long> ids = new ArrayList<Long>();
		ids.add(typeID);
		
		return this.getMarketStats(ids, regionID).get(typeID);
	}
	
	public Map<Long, MarketStats> getMarketStats(List<Long> typeIDs, long regionID){
		List<String> params = new ArrayList<String>();
		
		for(Long id : typeIDs){
			params.add("typeid=" + id);
		}
		params.add("regionlimit=" + regionID);
		params.add("hours=" + 1);
		
		System.out.println(new Date().getTime() + " - Calling web servers.");
		
		Document marketStatsDocument = this.executeRequest(API_URL, RESOURCE_MARKETSTATS, params);
		
		System.out.println(new Date().getTime() + " - Parsing response.");
		
		// Retreiving BuyStats
		Map<Long, MarketStats> marketStats = new HashMap<Long, MarketStats>();
		for(Long typeID : typeIDs){
			marketStats.put(
				typeID,
				new MarketStats(
					this.extractStat(typeID, Types.buy, marketStatsDocument), 
					this.extractStat(typeID, Types.sell, marketStatsDocument),
					this.extractStat(typeID, Types.all, marketStatsDocument)
				)
			);
		}
		
		System.out.println(new Date().getTime() + " - Parsing completed.");
		
		return marketStats;
	}
	
	private Stat extractStat(long typeID, Types type, Document document){
		String rootPath = "/evec_api/marketstat/type[@id='" + typeID + "']/" + type + "/";
		
		return new Stat(
			Long.parseLong(EVEConnector.extractField(rootPath + "volume", document)), 
			Double.parseDouble(EVEConnector.extractField(rootPath + "avg", document)), 
			Double.parseDouble(EVEConnector.extractField(rootPath + "max", document)), 
			Double.parseDouble(EVEConnector.extractField(rootPath + "min", document)), 
			Double.parseDouble(EVEConnector.extractField(rootPath + "stddev", document)), 
			Double.parseDouble(EVEConnector.extractField(rootPath + "percentile", document))
		);
	}
}
