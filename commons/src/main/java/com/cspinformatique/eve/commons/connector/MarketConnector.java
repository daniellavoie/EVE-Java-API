package com.cspinformatique.eve.commons.connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cspinformatique.eve.commons.exception.InvalidStationException;
import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.model.Station;
import com.cspinformatique.eve.commons.model.market.ItemResellStats;
import com.cspinformatique.eve.commons.model.market.MarketOrder;
import com.cspinformatique.eve.commons.model.market.MarketStats;
import com.cspinformatique.eve.commons.model.market.Stat;
import com.cspinformatique.eve.commons.model.market.Stat.Types;

@Component
public class MarketConnector extends RestConnector {
	private static final String API_URL = "http://api.eve-central.com";
	private static final String RESOURCE_MARKETSTATS = "/api/marketstat";
	private static final String RESOURCE_MARKETORDERS = "api/quicklook";
	
	public MarketStats getMarketStats(long typeID, long regionID){
		List<Long> ids = new ArrayList<Long>();
		ids.add(typeID);
		
		return this.getMarketStats(ids, regionID).get(typeID);
	}
	
	public ItemResellStats getLowestSellOrder(
		Item item,
		Station station
	){
		// Defining parameters.
		List<String> params = new ArrayList<String>();
		params.add("typeid=" + item.getTypeID());
		params.add("regionlimit=" + station.getRegion().getId());
		
		// Lancement de la requête REST sur EVE Central.
		Document marketOrdersDocument =	this.executeRequest(
											API_URL, 
											RESOURCE_MARKETORDERS, 
											params
										);
		
		// Step 1 - Run through all the selling orders.
		MarketOrder lowestSellOrder =	this.extractMarketOrder(
											item,
											station,
											marketOrdersDocument, 
											"/evec_api/quicklook/sell_orders", 
											false
										);
		
		// Step 2 - Run through all the buying orders.
		MarketOrder highestBuyOrder =	this.extractMarketOrder(
											item,
											station,
											marketOrdersDocument, 
											"/evec_api/quicklook/buy_orders", 
											true
										);
		
		if(lowestSellOrder.getBidPrice() != 0d && highestBuyOrder.getBidPrice() != 0d){
			// Calculating ItemResultStats based on the retreived stats.		
			return new ItemResellStats(
				0,
				item, 
				station, 
				highestBuyOrder.getBidPrice(), 
				lowestSellOrder.getBidPrice(), 
				new Date()
			);
		}else{
			System.out.println("No sell order or buy order found for item " + item.getTypeID());
			
			return null;
		}
	}
	
	private MarketOrder extractMarketOrder(
		Item item,
		Station station,
		Document document, 
		String xPathExpr,
		boolean extractHighest
	){
		MarketOrder referenceOrder = new MarketOrder(0, item, station, 0d, new Date());
		
		NodeList nodeList =	EVEConnector.getNodeList(xPathExpr, document);
		
		for(int i = 0; i < nodeList.getLength(); i++){
			if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element orderElement = (Element)nodeList.item(i);
				try{
					MarketOrder currentOrder =	new MarketOrder(
													0, 
													item, 
													station, 
													0d, 
													new Date()
												);
					
					// Look for values nodes.
					NodeList orderValuesdNodes = orderElement.getChildNodes();
					for(int y = 0; y < orderValuesdNodes.getLength(); y ++){
						if(orderValuesdNodes.item(y).getNodeType() == Node.ELEMENT_NODE){
							Element orderValueElement = (Element)orderValuesdNodes.item(y);
							if(orderValueElement.getNodeName().equals("station")){
								// Handling Station node.
								if(!orderValueElement.getTextContent().trim().equals(
									String.valueOf(referenceOrder.getStation().getId()))
								){
									throw new InvalidStationException();
								}
								
							}else if(orderValueElement.getNodeName().equals("price")){
								// Handling price node.
								currentOrder.setBidPrice(
									Double.parseDouble(orderValueElement.getTextContent())
								);
							}
						}
						
						// Comparing the current node and the current reference.
						if(	(	extractHighest && 
								currentOrder.getBidPrice()  > referenceOrder.getBidPrice()
							) || 
							(	!extractHighest && 
								currentOrder.getBidPrice() < referenceOrder.getBidPrice()
								
							)
						){
							referenceOrder = currentOrder;
						}
					}
				}catch(InvalidStationException invalidStationEx){
					// Station returned by EVE Central different from the requested one.
					// Skipping node.
				}
			}
		}
		
		return referenceOrder;
	}
	
	public Map<Long, MarketStats> getMarketStats(List<Long> typeIDs, long regionID){
		Map<Long, MarketStats> marketStats = new HashMap<Long, MarketStats>();
		int idProccessed = 0;
		
		do{
			List<String> params = new ArrayList<String>();
			
			for(int i = 0; i < 100 && idProccessed + i < typeIDs.size(); i++){
				params.add("typeid=" + typeIDs.get(idProccessed + i));
			}
			
			params.add("regionlimit=" + regionID);
			params.add("hours=" + 1);
			
			System.out.println(new Date().getTime() + " - Calling web servers.");
			
			Document marketStatsDocument =	this.executeRequest(
												API_URL, 
												RESOURCE_MARKETSTATS, 
												params
											);
			
			System.out.println(new Date().getTime() + " - Parsing response.");
			
			// Retreiving BuyStats
			for(int i = 0; i < 100 && idProccessed + i < typeIDs.size(); i++){
				long typeID = typeIDs.get(idProccessed + i);
				try{
					marketStats.put(
						typeID,
						new MarketStats(
							this.extractStat(typeID, Types.buy, marketStatsDocument), 
							this.extractStat(typeID, Types.sell, marketStatsDocument),
							this.extractStat(typeID, Types.all, marketStatsDocument)
						)
					);
				}catch(NoResultException noResultEx){
					System.out.println("No market data found for typeID " + typeID + ".");
				}
			}
			
			idProccessed += 100;
			
			System.out.println(new Date().getTime() + " - Parsing completed.");
		}while(idProccessed < typeIDs.size());
		
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
