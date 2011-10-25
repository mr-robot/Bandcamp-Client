package com.wonderfulrobot.BandCampAPILibraryExample.test;


import junit.framework.TestCase;

import com.wonderfulrobot.bandcampapi.core.BandcampException;
import com.wonderfulrobot.bandcampapi.core.BandcampService;
import com.wonderfulrobot.bandcampapi.core.BandcampServiceFactory;
import com.wonderfulrobot.bandcampapi.util.ServiceConfiguration;

public class TestBandcampFactory extends TestCase {
	


	public TestBandcampFactory() {
	}

	public TestBandcampFactory(String name) {
		super(name);
	}
	
	public void setUp(){}
	
	public void tearDown(){}
	
	public void testCreateDefault(){
		BandcampServiceFactory factory = new BandcampServiceFactory();
		
		BandcampService service = factory.buildService(TestConstants.key, TestConstants.secret);
		
		assertNotNull(service);
	}
	

	public void testCreateWithConfig(){
		BandcampServiceFactory factory = new BandcampServiceFactory();
		
		BandcampService service;
		try {
			service = factory.buildService(new ServiceConfiguration().keys(TestConstants.key, TestConstants.secret));
			

			assertNotNull(service);
		} catch (BandcampException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testCreateWithNullConfig(){
		BandcampServiceFactory factory = new BandcampServiceFactory();
		
		Exception e = null;
		try{
			BandcampService service = factory.buildService(null);
		}
		catch (Exception ee) {
			e = ee;
		}

		assertNotNull(e);
	}
}
