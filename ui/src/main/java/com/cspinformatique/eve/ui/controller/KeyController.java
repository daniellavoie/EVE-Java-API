package com.cspinformatique.eve.ui.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspinformatique.eve.commons.exception.EveException;
import com.cspinformatique.eve.commons.model.AccessMask;
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.service.CharacterService;
import com.cspinformatique.eve.commons.service.KeyService;
import com.cspinformatique.eve.commons.service.UserService;

@Controller
@Transactional
@RequestMapping("/key")
public class KeyController {
	@Autowired private CharacterService characterService;
	@Autowired private KeyService keyService;
	@Autowired private UserService userService;
	
	@RequestMapping(params="save")
	public String addKey(Model model, Key key){
		try{
			// Testing the key. 
			this.characterService.getCharacters(key);
			
			// Persisting the key.
			this.keyService.registerNewKey(key);
		}catch(EveException eveEx){
			model.addAttribute("keys", keyService.getUserKeys(key.getUser().getId()));
			model.addAttribute("key", key);
			model.addAttribute("error", eveEx.getMessage());
		}
		
		return "redirect:/key";
	}
	
	@RequestMapping(params={"corporation", "json"})
	public @ResponseBody boolean hasCorporationAssets(Principal principal){
		for(Key key : keyService.getUserKeys(userService.getUser(principal.getName()).getId())){
			if(	key.getType().equals(Key.TYPE_CORPORATION) && 
				keyService.hasAccessTo(key, AccessMask.CorporationAssetList)
			){
				return true;
			}
		}
		
		return false;
	}
	
	@RequestMapping(params={"hasAccessTo", "json"}, method=RequestMethod.POST)
	public @ResponseBody boolean hasAccessTo(Principal principal, @RequestBody String accessMask){
		return this.userService.hasAccessTo(
			principal.getName(), 
			AccessMask.valueOf(accessMask)
		);
	}
	
	@RequestMapping(value="/{key}", params="delete")
	public String deleteKey(Model model, @PathVariable Key key){
		this.keyService.deleteKey(key.getKeyID());
		
		return "redirect:/key";
	}
	
	@RequestMapping(params="json")
	public @ResponseBody List<Key> getKeys(Principal principal){
		return keyService.getUserKeys(userService.getUser(principal.getName()).getId());
	}
	
	@RequestMapping
	public String loadKeyMaintenancePage(Model model, Principal principal){
		int userId = userService.getUser(principal.getName()).getId();
		
		model.addAttribute("keys", keyService.getUserKeys(userId));
		model.addAttribute(
			"key", 
			new Key("", "", userService.getUser(userId), 0l, "", new ArrayList<AccessMask>())
		);
		
		return "key/management";
	}
}
