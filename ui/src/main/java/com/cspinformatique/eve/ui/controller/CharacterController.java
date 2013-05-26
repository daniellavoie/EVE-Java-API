package com.cspinformatique.eve.ui.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.eve.commons.model.Asset;
import com.cspinformatique.eve.commons.model.Character;
import com.cspinformatique.eve.commons.service.AssetService;
import com.cspinformatique.eve.commons.service.CharacterService;

@Controller
@RequestMapping("/character")
public class CharacterController {
	private static final long DEFAULT_REGION_ID = 10000002;
	@Autowired private AssetService assetService;
	@Autowired private CharacterService characterService;
	
	@PostConstruct
	public void init(){
		
	}
	
	@RequestMapping
	public @ResponseBody List<Character> getCharacters(
		@RequestParam String keyID, 
		@RequestParam String vCode
	){
		return this.characterService.getCharacters(keyID, vCode);
	}
	
	@RequestMapping("/{characterID}")
	public @ResponseBody Character getCharacter(
		@PathVariable long characterID, 
		@RequestParam String keyID,
		@RequestParam String vCode
	){
		return this.characterService.getCharacter(characterID, keyID, vCode);
	}
	
	@RequestMapping("/{characterID}/asset")
	public @ResponseBody List<Asset> getCharacterAssets(
		@PathVariable long characterID, 
		@RequestParam String keyID,
		@RequestParam String vCode
	){
		return this.assetService.loadCharacterAssets(
			this.characterService.getCharacter(characterID, keyID, vCode),
			DEFAULT_REGION_ID
		);
	}
}
