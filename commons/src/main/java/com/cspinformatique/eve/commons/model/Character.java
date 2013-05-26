package com.cspinformatique.eve.commons.model;

public class Character {
	private long eveId;
	private String keyID;
	private String vCode;
	
	private String name;
	private long corporationId;
	
	public Character(){
		
	}

	public Character(long eveId, String keyID, String vCode, String name,
			long corporationId) {
		super();
		this.eveId = eveId;
		this.keyID = keyID;
		this.vCode = vCode;
		this.name = name;
		this.corporationId = corporationId;
	}

	public long getEveId() {
		return eveId;
	}

	public void setEveId(long eveId) {
		this.eveId = eveId;
	}

	public String getKeyID() {
		return keyID;
	}

	public void setKeyID(String keyID) {
		this.keyID = keyID;
	}

	public String getvCode() {
		return vCode;
	}

	public void setvCode(String vCode) {
		this.vCode = vCode;
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
