package com.cspinformatique.eve.commons.model.market;

public class Stat {
	public enum Types{
		buy, sell, all;
	}
	
	private long volume;
	private double avg;
	private double max;
	private double min;
	private double stddev;
	private double percentile;
	
	public Stat(){
		
	}
	
	public Stat(
		long volume, 
		double avg, 
		double max, 
		double min, 
		double stddev,
		double percentile
	){
		this.volume = volume;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.stddev = stddev;
		this.percentile = percentile;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getStddev() {
		return stddev;
	}

	public void setStddev(double stddev) {
		this.stddev = stddev;
	}

	public double getPercentile() {
		return percentile;
	}

	public void setPercentile(double percentile) {
		this.percentile = percentile;
	}
}
