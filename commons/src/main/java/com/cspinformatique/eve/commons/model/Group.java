package com.cspinformatique.eve.commons.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="invGroups")
public class Group {
	private long id;
	private long categoryId;
	
	public Group(){
		
	}
	
	public Group(long id, long categoryId){
		this.id = id;
		this.categoryId = categoryId;
	}

	@Id
	@Column(name="groupID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="categoryID")
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
}	
