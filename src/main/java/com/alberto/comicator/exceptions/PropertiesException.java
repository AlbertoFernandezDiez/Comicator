package com.alberto.comicator.exceptions;

public class PropertiesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7869586415251856499L;

	public PropertiesException(String msg, Exception e) {
		super(msg, e);
	}

	public PropertiesException(String msg) {
		super(msg);
	}
}
