package com.cspinformatique.eve.commons.model.comparator;

import java.util.Comparator;

import com.cspinformatique.eve.commons.model.Asset;

public class AssetComparator implements Comparator<Asset>{
	public enum SortBy{
		NAME,
		SYSTEM_NAME,
		STATION_NAME,
		QUANTITY,
		UNITARY_PRICE,
		WHOLE_PRICE
	}
	
	private boolean ascending;
	private SortBy sortBy;
	
	
	public AssetComparator(SortBy sortBy, boolean ascending){
		this.sortBy = sortBy;
		this.ascending = ascending;
	}
	
	@Override
	public int compare(Asset asset1, Asset asset2) {
		int result = 0;
		if(this.sortBy == SortBy.NAME){
			result = this.compareName(asset1, asset2);
			if(result == 0){
				result = this.compareSolarSystem(asset1, asset2);
				if(result == 0){
					result = this.compareStationName(asset1, asset2);
				}
			}else if(!this.ascending){
				result = result * -1;
			}
		}else if(this.sortBy == SortBy.SYSTEM_NAME){
			result = this.compareSolarSystem(asset1, asset2);
			if(result == 0){
				result = this.compareStationName(asset1, asset2);
				
				if(result == 0){
					result = this.compareName(asset1, asset2);
				}
			}else if(!this.ascending){
				result = result * -1;
			}
		}else if(this.sortBy == SortBy.STATION_NAME){
			result = this.compareStationName(asset1, asset2);
			if(result == 0){
				result = this.compareSolarSystem(asset1, asset2);
				if(result == 0){
					result = this.compareName(asset1, asset2);
				}
			}else if(!this.ascending){
				result = result * -1;
			}
		}else if(this.sortBy == SortBy.QUANTITY){
			result = this.compareQuantity(asset1, asset2);
			if(result == 0){
				result = this.compareName(asset1, asset2);
				if(result == 0){
					result = this.compareSolarSystem(asset1, asset2);
					if(result == 0){
						result = this.compareStationName(asset1, asset2);
					}
				}
			}else{
				result = result * -1;
			}
		}else if(this.sortBy == SortBy.UNITARY_PRICE){
			result = this.compareUnitaryPrice(asset1, asset2);
			if(result == 0){
				result = this.compareName(asset1, asset2); 
			}else if(!this.ascending){
				result = result * -1;
			}
		}else if(this.sortBy == SortBy.WHOLE_PRICE){
			result = this.compareWholePrice(asset1, asset2);
			if(result == 0){
				result = this.compareName(asset1, asset2);
			}else if(!this.ascending){
				result = result * -1;
			}
		}
		
		return result;
	}
	
	private int compareName(Asset asset1, Asset asset2){
		return asset1.getItem().getName().compareTo(asset2.getItem().getName());
	}
	
	private int compareSolarSystem(Asset asset1, Asset asset2){
		return asset1.getStation().getSolarSystem().getName().compareTo(
			asset2.getStation().getSolarSystem().getName()
		);
	}
	
	private int compareStationName(Asset asset1, Asset asset2){
		return asset1.getStation().getName().compareTo(
			asset2.getStation().getName()
		);
	}
	
	private int compareQuantity(Asset asset1, Asset asset2){
		if(asset1.getQuantity() < asset2.getQuantity()){
			return -1;
		}else if(asset1.getQuantity() > asset2.getQuantity()){
			return 1;
		}else{
			return 0;
		}
	}
	
	private int compareUnitaryPrice(Asset asset1, Asset asset2){
		if(	asset1.getMarketStats().getSellStats().getAvg() <
				asset2.getMarketStats().getSellStats().getAvg()
		){
			return -1;
		}else if(asset1.getMarketStats().getSellStats().getAvg() >
				asset2.getMarketStats().getSellStats().getAvg()
		){
			return 1;
		}else{
			return 0;
		}
	}
	
	private int compareWholePrice(Asset asset1, Asset asset2){
		if(	asset1.getMarketStats().getSellStats().getAvg() * asset1.getQuantity() <
				asset2.getMarketStats().getSellStats().getAvg() * asset1.getQuantity()
		){
			return -1;
		}else if(asset1.getMarketStats().getSellStats().getAvg() * asset1.getQuantity() >
				asset2.getMarketStats().getSellStats().getAvg() * asset1.getQuantity()
		){
			return 1;
		}else{
			return 0;
		}
	}
}
