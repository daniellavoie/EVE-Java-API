package com.cspinformatique.eve.commons.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cspinformatique.eve.commons.model.Item;
import com.cspinformatique.eve.commons.repository.ItemRepository;

@Repository
public class ItemRepositoryJdbcImpl implements ItemRepository {
	private static final String SQL_SELECT_TYPE = 
		"SELECT " +
		"	typeName, " +
		"	description, " +
		"	marketGroupID, " +
		"	basePrice, " +
		"	raceID " +
		"FROM " +
		"	invTypes " +
		"WHERE " +
		"	typeID = ?";
	
	@Autowired private JdbcTemplate eveJdbcTemplate;
	
	@Override
	public Item getItem(final long id) {
		return eveJdbcTemplate.queryForObject(
			SQL_SELECT_TYPE, 
			new RowMapper<Item>(){
				@Override
				public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
					String description = rs.getString("description");
					if(description == null) description = "";
					
					boolean tradable = false;
					if(	rs.getLong("marketGroupID") != 0l ||
						rs.getInt("raceID") != 0l
					){
						tradable = true;
					}
					return new Item(id, rs.getString("typeName"), description, tradable);
				}
			},
			id
		);
	}
}
