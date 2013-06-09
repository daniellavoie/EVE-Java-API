package com.cspinformatique.eve.commons.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cspinformatique.eve.commons.model.SolarSystem;
import com.cspinformatique.eve.commons.repository.eve.SolarSystemRepository;
import com.cspinformatique.eve.commons.service.SolarSystemService;

@Service
@Transactional("eveTransactionManager")
public class SolarSystemServiceImpl implements SolarSystemService {
	@Autowired
	private SolarSystemRepository solarSystemRepository;
	
	@Override
	public SolarSystem getSolarSystem(long id) {
		return this.solarSystemRepository.findOne(id);
	}
}
