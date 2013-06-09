package com.cspinformatique.eve.commons.exception;

public class EveException extends RuntimeException {
	private static final long serialVersionUID = 6281886778153039786L;
	
	public EveException(){
		super();
	}
	
	public EveException(String message){
		super(message);
	}
	
	public EveException(Throwable cause){
		super(cause);
	}
}
