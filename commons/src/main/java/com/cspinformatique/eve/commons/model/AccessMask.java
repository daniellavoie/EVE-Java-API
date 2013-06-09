package com.cspinformatique.eve.commons.model;

import java.util.Arrays;
import java.util.List;

public enum AccessMask {
	CharacterAssetList(2),
	
	CorporationAssetList(2);
	
	private int bit;
	
	private AccessMask(int bit){
		this.bit = bit;
	}
	
	public int getBit(){
		return this.bit;
	}
	
	public static List<AccessMask> characterValues(){
		return Arrays.asList(new AccessMask[]{
			CharacterAssetList
		});
	}
	
	public static List<AccessMask> corporationValues(){
		return Arrays.asList(new AccessMask[]{
			CorporationAssetList
		});
	}
}