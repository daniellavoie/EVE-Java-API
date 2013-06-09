package com.cspinformatique.eve.commons.exception;

public class UserNotFoundException extends EveException {
	private static final long serialVersionUID = 8722993824759242134L;
	
	public UserNotFoundException(){
		super();
	}
	
	public UserNotFoundException(String message){
		super(message);
	}
	
	public UserNotFoundException(Throwable cause){
		super(cause);
	}
}
