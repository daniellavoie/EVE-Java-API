package com.cspinformatique.eve.commons.connector;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

public class EVEConnector extends RestConnector {
	private static final String EVE_API_URL = "https://api.eveonline.com";
	
	public Document executeRequest(String keyID, String vCode, String resource){
		return this.executeRequest(keyID, vCode, resource, null);
	}
	
	public Document executeRequest(String keyID, String vCode, String resource, List<String> params){
		if(params == null){
			params = new ArrayList<String>();
		}
		
		params.add("keyID=" + keyID);
		params.add("vCode=" + vCode);
		
		return this.executeRequest(EVE_API_URL, resource, params);
	}
}
