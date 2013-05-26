package com.cspinformatique.eve.commons.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.eve.commons.model.Location;
import com.cspinformatique.eve.commons.repository.LocationRepository;
import com.cspinformatique.eve.commons.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	@Autowired private LocationRepository locationRepository;
	@Override
	public Location getLocation(long id) {
		return this.locationRepository.getLocation(id);
	}

}
