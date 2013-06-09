package com.cspinformatique.eve.commons.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mapSolarSystems")
public class SolarSystem {
	private long id;
	private String name;
	private Region region;

	public SolarSystem() {
		
	}

	public SolarSystem(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name="solarSystemID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="solarSystemName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "regionID", nullable = false)
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}
