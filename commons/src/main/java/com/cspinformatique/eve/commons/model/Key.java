package com.cspinformatique.eve.commons.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(schema="jeve")
public class Key {
	public static final String TYPE_CHARACTER = "Character";
	public static final String TYPE_CORPORATION = "Corporation";
	
	private long accessMask;
	private String keyID;
	private String vCode;
	private String type;
	private List<AccessMask> grants;
	
	@JsonBackReference
	private User user;
	
	public Key(){
		
	}

	public Key(
		String keyID, 
		String vCode, 
		User user, 
		long accessMask, 
		String type, 
		List<AccessMask> grants
	) {
		this.keyID = keyID;
		this.vCode = vCode;
		this.user = user;
		this.accessMask = accessMask;
		this.type = type;
		this.grants = grants;
	}

	@Id
	@Column(nullable=false, unique=true)
	public String getKeyID() {
		return keyID;
	}

	public void setKeyID(String keyID) {
		this.keyID = keyID;
	}

	@Column(nullable=false)
	public String getvCode() {
		return vCode;
	}

	public void setvCode(String vCode) {
		this.vCode = vCode;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usr_id", nullable=false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public long getAccessMask() {
		return accessMask;
	}

	public void setAccessMask(long accessMask) {
		this.accessMask = accessMask;
	}

	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Transient
	public List<AccessMask> getGrants() {
		return grants;
	}

	public void setGrants(List<AccessMask> grants) {
		this.grants = grants;
	}
}
