package com.cspinformatique.eve.commons.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mapRegions")
public class Region {
	public long id;
	public String name;
	
	public Region(){
		
	}
	
	public Region(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name="regionID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="regionName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
