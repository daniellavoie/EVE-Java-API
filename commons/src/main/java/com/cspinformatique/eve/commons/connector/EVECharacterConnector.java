package com.cspinformatique.eve.commons.connector;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cspinformatique.eve.commons.model.Character;

@Component
public class EVECharacterConnector extends EVEConnector {
	private static final String CHARACTERS_RESOURCE =
		"/account/Characters.xml.aspx";
	
	public List<Character> getCharacters(String keyID, String vCode){
		Document charactersDocument = super.executeRequest(keyID, vCode, CHARACTERS_RESOURCE);
		
		NodeList charactersNodeList =	EVEConnector.getNodeList(
											"/eveapi/result/rowset/row", 
											charactersDocument
										);
		
		List<Character> characters = new ArrayList<Character>();
		for(int i = 0; i < charactersNodeList.getLength(); i++){
			Node node = charactersNodeList.item(i);
			if(node instanceof Element){
				Element characterElement = (Element)node;
				
				characters.add(
					new Character(
						Long.parseLong(characterElement.getAttribute("characterID")),
						keyID,
						vCode,
						characterElement.getAttribute("name"),
						Long.parseLong(characterElement.getAttribute("corporationID"))
					)
				);
			}
		}
		
		return characters;
		
	}
	
	public Document executeRequest(Character character, String resource){
		return super.executeRequest(character.getKeyID() , character.getvCode(), resource);
	}
	
	public Document executeRequest(Character character, String resource, List<String> params){
		return super.executeRequest(character.getKeyID() , character.getvCode(), resource, params);
	}
}
