package com.cspinformatique.eve.commons.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="invTypes")
public class Item {
	private Long typeID;
	private String name;
	private String description;
	private Group group;
	private Long marketGroupId;
	private Integer raceId;
	
	public Item(){
		
	}
	
	public Item(
		long typeID, 
		String name, 
		String description,
		Group group,
		long marketGroupId,
		int raceId
	) {
		this.typeID = typeID;
		this.name = name;
		this.description = description;
		this.group = group;
		this.marketGroupId = marketGroupId;
		this.raceId = raceId;
	}

	@Id
	@Column(name="typeID")
	public long getTypeID() {
		return typeID;
	}

	public void setTypeID(long typeID) {
		this.typeID = typeID;
	}

	@Column(name="typeName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean tradable() {
		if(this.getMarketGroupId() == null){
			return false;
		}
		
		return true;
	}

	public boolean hasInventory() {
		if(this.getTypeID() == 27 || this.getGroup().getCategoryId() == 6){
			return true;
		}
		
		return false;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="groupID", nullable=false)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	@Column(name="marketGroupID")
	public Long getMarketGroupId() {
		return marketGroupId;
	}

	public void setMarketGroupId(Long marketGroupId) {
		this.marketGroupId = marketGroupId;
	}

	@Column(name="raceID")
	public Integer getRaceId() {
		return raceId;
	}

	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}
}
