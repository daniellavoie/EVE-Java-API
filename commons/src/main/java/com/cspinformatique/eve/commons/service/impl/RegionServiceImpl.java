package com.cspinformatique.eve.commons.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.eve.commons.model.Region;
import com.cspinformatique.eve.commons.repository.RegionRepository;
import com.cspinformatique.eve.commons.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService{
	@Autowired private RegionRepository regionRepository;
	
	@Override
	public Region getRegion(long id){
		return this.regionRepository.getRegion(id);
	}
}
