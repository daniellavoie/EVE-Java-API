package com.cspinformatique.eve.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.service.ItemService;

@Controller
@Transactional
@RequestMapping("/item")
public class ItemController {
	private static final long DEFAULT_REGION_ID = 10000002;
	
	@Autowired private ItemService itemService;
	
	private List<Asset> bestTradableItems;
	
	@RequestMapping(params="bestTradable")
	public String findBestTradableItems(Model model){
		if(bestTradableItems == null){
			bestTradableItems = itemService.findBestTradableItems(DEFAULT_REGION_ID); 
		}
		
		model.addAttribute(
			"bestTradableItems", 
			bestTradableItems
		);
		
		return "item/bestTradableItems";
	}
}
