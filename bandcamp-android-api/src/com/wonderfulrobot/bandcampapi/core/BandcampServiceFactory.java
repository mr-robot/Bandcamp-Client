package com.wonderfulrobot.bandcampapi.core;

import com.wonderfulrobot.bandcampapi.util.ServiceConfiguration;

public class BandcampServiceFactory {
	
	/**
	 * 
	 * @param apiKey
	 * @param secret
	 * @return
	 */
	public BandcampService buildService(String apiKey, String secret){
		return new BandcampService(buildDefaultConfig(apiKey,secret));
	}
	
	/**
	 * 
	 * @param config
	 * @return
	 * @throws BandcampException
	 */
	public BandcampService buildService(ServiceConfiguration config) throws BandcampException{

		if(config != null)
			return new BandcampService(config);
		else
			throw new BandcampException("Null ServiceConfiguration");
	}
	
	private ServiceConfiguration buildDefaultConfig(String apiKey, String secret) {
		return new ServiceConfiguration()
		.setGzip(true)
		.setSocketTimeout(3000)
		.setTimeout(5000)
		.keys(apiKey, secret);
	}

}
