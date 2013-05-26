package com.cspinformatique.eve.commons.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cspinformatique.eve.commons.model.Location;
import com.cspinformatique.eve.commons.repository.LocationRepository;

@Repository
public class LocationRepositoryJdbcImpl implements LocationRepository {
	private static final String SQL_SELECT_LOCATION =
		"SELECT " +
		"	solarSystemName, " +
		"	stationName " + 
		"from " +
		"	staStations, " +
		"	mapSolarSystems " +
		"where " +
		"	staStations.solarSystemID = mapSolarSystems.solarSystemID AND " +
		"	staStations.stationID = ?";
	
	@Autowired private JdbcTemplate eveJdbcTemplate;
	
	@Override
	public Location getLocation(final long id) {
		return this.eveJdbcTemplate.queryForObject(
			SQL_SELECT_LOCATION, 
			new RowMapper<Location>(){
				@Override
				public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Location(
						id, 
						rs.getString("stationName"), 
						rs.getString("solarSystemName")
					);
				}
			},
			id
		);
	}

}
