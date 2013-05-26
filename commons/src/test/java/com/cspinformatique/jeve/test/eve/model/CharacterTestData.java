package com.cspinformatique.jeve.test.eve.model;

import com.cspinformatique.eve.commons.model.Character;

public abstract class CharacterTestData {
	public static Character getCharacter(){
		return new Character(
			93360906,
			"2159603", 
			"UALNPVzdTeT4BTvjciwr2oOAnMiuIEGcMvHg544GSERFsYMJYie6P1ENsbsE2tZ5",
			"Dan Eistiras",
			1000045
		); 
	}
}
