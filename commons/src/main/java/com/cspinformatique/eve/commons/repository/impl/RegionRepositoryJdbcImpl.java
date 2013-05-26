package com.cspinformatique.eve.commons.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cspinformatique.eve.commons.model.Region;
import com.cspinformatique.eve.commons.repository.RegionRepository;

@Repository
public class RegionRepositoryJdbcImpl implements RegionRepository{	
	private static final String SQL_SELECT_REGION =
		"SELECT" +
		"	regionName " +
		"FROM " +
		"	mapRegions " +
		"WHERE " +
		"	regionID : ?";
	
	@Autowired private JdbcTemplate eveJdbcTemplate;
	
	@Override
	public Region getRegion(final long id){
		return this.eveJdbcTemplate.queryForObject(
			SQL_SELECT_REGION, 
			new RowMapper<Region>(){
				@Override
				public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Region(id, rs.getString("regionName"));
				}
			},
			id
		);
	}
}
