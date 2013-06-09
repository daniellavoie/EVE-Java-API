package com.cspinformatique.eve.ui.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.model.comparator.AssetComparator;
import com.cspinformatique.eve.commons.model.comparator.AssetComparator.SortBy;
import com.cspinformatique.eve.commons.service.AssetService;
import com.cspinformatique.eve.commons.service.CharacterService;
import com.cspinformatique.eve.commons.service.KeyService;
import com.cspinformatique.eve.commons.service.UserService;

@Controller
@Transactional
@RequestMapping("/corporation")
public class CorporationController {
	private static final long DEFAULT_REGION_ID = 10000002;
	
	@Autowired private AssetService assetService;
	@Autowired private CharacterService characterService;
	@Autowired private KeyService keyService;
	@Autowired private UserService userService;	
	
	@RequestMapping("/{characterID}/asset")
	public String getCorporationAssets(
		Model model,
		@PathVariable long characterID, 
		@RequestParam String vCode,
		@RequestParam SortBy sortBy,
		@RequestParam boolean ascending,
		Principal principal
	){
		model.addAttribute(
			"assets", 
			this.getCorporationAssets(characterID, sortBy, ascending, principal)
		);
		
		model.addAttribute("characterId", characterID);
		model.addAttribute(
			"characters", 
			characterService.getCharacters(
				userService.getUser(principal.getName())
			)
		);
		
		return "corporation/assets";
	}
	
	@RequestMapping(value="/{characterID}/asset", headers="Content-Type=application/json")
	public @ResponseBody List<Asset> getCorporationAssets(
		@PathVariable long characterID, 
		@RequestParam SortBy sortBy,
		@RequestParam boolean ascending,
		Principal principal
	){
		List<Asset> assets =	this.assetService.loadCorporationAssets(
									this.characterService.getCharacter(
										characterID, 
										this.keyService.getCharacterKey(principal.getName(), characterID)
									),
									DEFAULT_REGION_ID
								);
		
		Collections.sort(assets, new AssetComparator(sortBy, ascending));
		
		return assets;
	}
	
	@RequestMapping("/asset")
	public String getDefaultCorporationAssets(Model model, Principal principal){
		// Select a default character.
		Character defaultCorporation =	characterService.getCorporations(this.userService.getUser(
											principal.getName()
										)).get(0);
		
		// Redirect to main page.
		return 
			"redirect:/corporation/" + defaultCorporation.getEveId() + 
			"/asset" +
			"&sortBy=" + SortBy.NAME +
			"&ascending=true";
	}
}
