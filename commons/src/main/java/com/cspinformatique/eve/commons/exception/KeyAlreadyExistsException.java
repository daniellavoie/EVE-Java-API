package com.cspinformatique.eve.commons.exception;

public class KeyAlreadyExistsException extends EveException {
	private static final long serialVersionUID = 3463640578267651010L;
	
	public KeyAlreadyExistsException(){
		super();
	}
	
	public KeyAlreadyExistsException(String message){
		super(message);
	}
	
	public KeyAlreadyExistsException(Throwable cause){
		super(cause);
	}
}
