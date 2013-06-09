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
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.model.comparator.AssetComparator;
import com.cspinformatique.eve.commons.model.comparator.AssetComparator.SortBy;
import com.cspinformatique.eve.commons.service.AssetService;
import com.cspinformatique.eve.commons.service.CharacterService;
import com.cspinformatique.eve.commons.service.KeyService;
import com.cspinformatique.eve.commons.service.UserService;

@Controller
@Transactional
@RequestMapping("/character")
public class CharacterController {
	private static final long DEFAULT_REGION_ID = 10000002;
	
	@Autowired private AssetService assetService;
	@Autowired private CharacterService characterService;
	@Autowired private KeyService keyService;
	@Autowired private UserService userService;
	
	@RequestMapping(headers="Content-Type=application/json")
	public @ResponseBody List<Character> getCharacters(
		@RequestParam Key key
	){
		return this.characterService.getCharacters(key);
	}
	
	@RequestMapping("/{characterID}")
	public @ResponseBody Character getCharacter(
		@PathVariable long characterID, 
		@RequestParam Key key
	){
		return this.characterService.getCharacter(characterID, key);
	}
	
	@RequestMapping("/{characterID}/asset")
	public String getCharacterAssets(
		Model model,
		@PathVariable long characterID,
		@RequestParam SortBy sortBy,
		@RequestParam boolean ascending,
		Principal principal
	){
		model.addAttribute(
			"assets", 
			this.getCharacterAssets(characterID, sortBy, ascending, principal)
		);
		
		model.addAttribute("characterId", characterID);
		model.addAttribute(
			"characters", 
			characterService.getCharacters(
				userService.getUser(principal.getName())
			)
		);
		
		return "character/assets";
	}
	
	@RequestMapping(value="/{characterID}/asset", headers="Content-Type=application/json")
	public @ResponseBody List<Asset> getCharacterAssets(
		@PathVariable long characterID, 
		@RequestParam SortBy sortBy,
		@RequestParam boolean ascending,
		Principal principal
	){
		List<Asset> assets =	this.assetService.loadCharacterAssets(
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
	public String getDefaultCharacterAssets(Model model, Principal principal){
		// Select a default character.
		Character defaultCharacter =	characterService.getCharacters(this.userService.getUser(
											principal.getName()
										)).get(0);
		
		// Redirect to main page.
		return 
			"redirect:/character/" + defaultCharacter.getEveId() + 
			"/asset" +
			"?sortBy=" + SortBy.NAME +
			"&ascending=true";
	}
}
