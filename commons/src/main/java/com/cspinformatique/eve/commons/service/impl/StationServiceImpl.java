package com.cspinformatique.eve.commons.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cspinformatique.eve.commons.model.Station;
import com.cspinformatique.eve.commons.repository.eve.StationRepository;
import com.cspinformatique.eve.commons.service.StationService;

@Service
@Transactional("eveTransactionManager")
public class StationServiceImpl implements StationService {
	@Autowired private StationRepository stationRepository;
	
	@Override
	public Station getStation(long id) {
		return this.stationRepository.findOne(id);
	}

}
