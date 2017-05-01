package com.alberto.comicator.exceptions;

public class FatalException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8822778921772404908L;
	
	public static final String MESSAGE_FATAL_ERROR = "FatalError, the exectin will stop";

	public FatalException(String msg, Exception e) {
		super(msg, e);
	}

	public FatalException(String msg) {
		super(msg);
	}
}
