package com.cspinformatique.eve.commons.model;

public class Location {
	private long id;
	private String stationName;
	private String solarSystemName;
	
	public Location(long id, String stationName, String solarSystemName) {
		this.id = id;
		this.stationName = stationName;
		this.solarSystemName = solarSystemName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getSolarSystemName() {
		return solarSystemName;
	}

	public void setSolarSystemName(String solarSystemName) {
		this.solarSystemName = solarSystemName;
	}
}
