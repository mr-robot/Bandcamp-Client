package com.wonderfulrobot.bandcampapi.util;

import com.wonderfulrobot.bandcampapi.core.BandcampConstants;

public class ServiceConfiguration {

	
	private boolean gzip;
	private int socketTimeout;
	private int connectionTimeout;
	private String secret;
	
	/**
	 * 
	 * Builds a Default Service Configuration
	 * 
	 */
	public ServiceConfiguration(){
		this.gzip = BandcampConstants.GZIP;
		this.socketTimeout = BandcampConstants.SOCKET_TIMEOUT;
		this.connectionTimeout = BandcampConstants.CONNECTION_TIMEOUT;
	}
	
	public boolean isGzip() {
		return gzip;
	}

	public ServiceConfiguration setGzip(boolean gzip) {
		this.gzip = gzip;
		return this;
	}

	public int getTimeout() {
		return socketTimeout;
	}

	public ServiceConfiguration setTimeout(int timeout) {
		this.socketTimeout = timeout;
		return this;
	}

	public String getSecret() {
		return secret;
	}

	public String getApiKey() {
		return apiKey;
	}

	private String apiKey;
	
	public int getSocketTimeout() {
		return socketTimeout;
	}

	public ServiceConfiguration setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public ServiceConfiguration keys(String apiKey, String secret) {
		this.apiKey = apiKey;
		this.secret = secret;
		return this;
	}
}
