package com.cspinformatique.eve.commons.model;

public class Item {
	private long typeID;
	private String name;
	private String description;
	private boolean tradable;
	
	public Item(long typeID, String name, String description, boolean tradable) {
		this.typeID = typeID;
		this.name = name;
		this.description = description;
		this.tradable = tradable;
	}

	public long getTypeID() {
		return typeID;
	}

	public void setTypeID(long typeID) {
		this.typeID = typeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getTradable() {
		return tradable;
	}

	public void isTradable(boolean tradable) {
		this.tradable = tradable;
	}
}
