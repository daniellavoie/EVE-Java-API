package com.cspinformatique.eve.commons.connector;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.cspinformatique.eve.commons.model.Key;

@Component
public class EVEConnector extends RestConnector {
	private static final String EVE_API_URL = "https://api.eveonline.com";
	
	public Document executeRequest(Key key, String resource){
		return this.executeRequest(key, resource, null);
	}
	
	public Document executeRequest(
		Key key,
		String resource, 
		List<String> params
	){
		try{
			if(params == null){
				params = new ArrayList<String>();
			}
			
			params.add("keyID=" + key.getKeyID());
			params.add("vCode=" + key.getvCode());
			
			Document response = this.executeRequest(EVE_API_URL, resource, params);
			
			String errorMsg = XPATH.evaluate("/eveapi/error", response);
			if(!errorMsg.equals("")){
				throw new AuthentificationFailedException(errorMsg);
			}
			
			return response;
		}catch(XPathExpressionException xPathExpressionEx){
			throw new RuntimeException(xPathExpressionEx);
		}
	}
}
