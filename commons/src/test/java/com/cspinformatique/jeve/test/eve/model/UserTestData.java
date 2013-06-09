package com.cspinformatique.jeve.test.eve.model;

import java.util.Arrays;

import com.cspinformatique.eve.commons.model.AccessMask;
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.model.User;

public abstract class UserTestData {
	public static User getUser(){
		User user =	new User( 
						1,
						"dlavoie", 
						"test", 
						null,
						"user"
					);
		
		user.setKeys(
			Arrays.asList(
				new Key[]{
					new Key(
						"2159603", 
						"UALNPVzdTeT4BTvjciwr2oOAnMiuIEGcMvHg544GSERFsYMJYie6P1ENsbsE2tZ5", 
						user,
						0l,
						Key.TYPE_CHARACTER,
						Arrays.asList(new AccessMask[]{AccessMask.CharacterAssetList})
					)
				}
			)	
		);
		
		return user;
	}
}
