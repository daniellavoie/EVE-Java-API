package com.cspinformatique.eve.commons.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(schema="jeve")
public class User {

	private int id;
	private String username;
	private String password;
	
	@JsonManagedReference
	private List<Key> keys;
	
	private String type;
	
	public User(){
		
	}
	
	public User(int id, String username, String password, List<Key> keys, String type) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.keys = keys;
		this.type = type;
	}

	@Id 
	@GeneratedValue 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(nullable=false, unique=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	public List<Key> getKeys() {
		return keys;
	}

	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
