package com.cspinformatique.eve.commons.model;

public class Character {
	private long eveId;
	private Key key;
	
	private String name;
	private long corporationId;
	
	public Character(){
		
	}

	public Character(long eveId, Key key, String name, long corporationId) {
		this.eveId = eveId;
		this.key = key;
		this.name = name;
		this.corporationId = corporationId;
	}

	public long getEveId() {
		return eveId;
	}

	public void setEveId(long eveId) {
		this.eveId = eveId;
	}

	public Key getKey(){
		return this.key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(long corporationId) {
		this.corporationId = corporationId;
	}
}
