package com.wonderfulrobot.BandCampAPILibraryExample.test;

import java.util.List;

import junit.framework.TestCase;

import com.wonderfulrobot.bandcampapi.core.BandcampException;
import com.wonderfulrobot.bandcampapi.core.BandcampService;
import com.wonderfulrobot.bandcampapi.core.BandcampServiceFactory;
import com.wonderfulrobot.bandcampapi.data.Album;
import com.wonderfulrobot.bandcampapi.data.Band;
import com.wonderfulrobot.bandcampapi.data.DiscographyItem;
import com.wonderfulrobot.bandcampapi.data.SearchResult;
import com.wonderfulrobot.bandcampapi.data.Track;

public class TestBandcampServiceErrors extends TestCase {
	
	private BandcampService mService;
	
	private long session_id;

	public TestBandcampServiceErrors() {
	}

	public TestBandcampServiceErrors(String name) {
		super(name);
	}
	
	public void setUp(){
		this.mService = new BandcampServiceFactory().buildService(TestConstants.key, TestConstants.secret);
	}
	

	
	public void testSearch(){
		try {
			SearchResult sr = mService.search("Sola Rosa");
			assertNull(sr);
			
			
		} catch (BandcampException e) {
			e.printStackTrace();
			assertNotNull(e);
		}

	}
	
	public void testSearchEmpty(){
		try {
			SearchResult sr = mService.search("");			
			assertNull(sr);
			
		} catch (BandcampException e) {
			assertNotNull(e);
			e.printStackTrace();
		}
	}
	
	public void testDiscography(){
		try {
			List<DiscographyItem> dItems = mService.getDiscography(new long[]{203035041L});
			assertNull(dItems);
			
			
		} catch (BandcampException e) {
			assertNotNull(e);
			e.printStackTrace();
		}
	}
	
	public void testDiscographyEmpty(){
		try {
			List<DiscographyItem> dItems = mService.getDiscography(new long[]{});
			assertNull(dItems);
			

			
		} catch (BandcampException e) {
			assertNotNull(e);
			e.printStackTrace();
		}
	}

	public void testBand(){
		try {
			Band band = mService.getBand(3463798201L);
			assertNull(band);
			
			
		} catch (BandcampException e) {
			assertNotNull(e);
			e.printStackTrace();
		}
	}
	
	public void testAlbum(){
		
		try {
			Album album = mService.getAlbum(2587417518L);
			assertNull(album);
			
		} catch (BandcampException e) {
			assertNotNull(e);
			e.printStackTrace();
		}
		
		
		
	}
	
	public void testTrack(){
		try {
			Track track = mService.getTrack(1269403107L);
			assertNull(track);
			
			
			//check for 12 tracks
		} catch (BandcampException e) {
			e.printStackTrace();
			assertNotNull(e);
		}
	}

}
