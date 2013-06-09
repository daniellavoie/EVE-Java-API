package com.cspinformatique.jeve.test.eve.model;

import com.cspinformatique.eve.commons.model.Character;

public abstract class CharacterTestData {
	public static Character getCharacter(){		
		return new Character(
			93360906,
			UserTestData.getUser().getKeys().get(0),
			"Dan Eistiras",
			1000045
		); 
	}
}
