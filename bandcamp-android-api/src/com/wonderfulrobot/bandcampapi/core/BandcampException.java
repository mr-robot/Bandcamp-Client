package com.wonderfulrobot.bandcampapi.core;

public class BandcampException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7086758131485388486L;

	public BandcampException() {
	}

	public BandcampException(String detailMessage) {
		super(detailMessage);
	}

	public BandcampException(Throwable throwable) {
		super(throwable);
	}

	public BandcampException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
