package com.cspinformatique.eve.commons.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="staStations")
public class Station {
	private long id;
	private String name;
	private SolarSystem solarSystem;
	private Region region;

	public Station() {
	
	}

	public Station(long id, String name, SolarSystem solarSystem, Region region) {
		this.id = id;
		this.name = name;
		this.solarSystem = solarSystem;
		this.region = region;
	}

	@Id
	@Column(name="stationID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="stationName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "solarSystemID", nullable = false)
	public SolarSystem getSolarSystem() {
		return solarSystem;
	}

	public void setSolarSystem(SolarSystem solarSystem) {
		this.solarSystem = solarSystem;
	}
	
	@ManyToOne
	@JoinColumn(name = "regionID", nullable = false)
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}
