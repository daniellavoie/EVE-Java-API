package com.cspinformatique.eve.commons.model.market;

public class MarketStats {
	protected Stat allStats;
	protected Stat buyStats;
	protected Stat sellStats;
	
	public MarketStats(){
		
	}
	
	public MarketStats(Stat allStats, Stat buyStats, Stat sellStats) {
		this.allStats = allStats;
		this.buyStats = buyStats;
		this.sellStats = sellStats;
	}

	public Stat getAllStats() {
		return allStats;
	}

	public void setAllStats(Stat allStats) {
		this.allStats = allStats;
	}

	public Stat getBuyStats() {
		return buyStats;
	}

	public void setBuyStats(Stat buyStats) {
		this.buyStats = buyStats;
	}
	
	public double getResellRatio(){
		return	(	this.getBuyStats().getAvg() - 
						this.getSellStats().getAvg()
				) / this.getBuyStats().getAvg();
	}

	public Stat getSellStats() {
		return sellStats;
	}

	public void setSellStats(Stat sellStats) {
		this.sellStats = sellStats;
	}
}
