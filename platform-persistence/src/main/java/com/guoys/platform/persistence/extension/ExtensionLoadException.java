package com.guoys.platform.persistence.extension;

public class ExtensionLoadException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	
	public ExtensionLoadException(String message){
		super(message);
	}
	
	public ExtensionLoadException(String message,Throwable e){
		super(message,e);
	}
}
