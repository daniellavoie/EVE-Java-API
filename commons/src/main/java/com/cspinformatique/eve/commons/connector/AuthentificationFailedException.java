package com.cspinformatique.eve.commons.connector;

import com.cspinformatique.eve.commons.exception.EveException;

public class AuthentificationFailedException extends EveException {
	private static final long serialVersionUID = -500090810480140406L;
	
	public AuthentificationFailedException(){
		super();
	}
	
	public AuthentificationFailedException(String message){
		super(message);
	}
	
	public AuthentificationFailedException(Throwable cause){
		super(cause);
	}
}
