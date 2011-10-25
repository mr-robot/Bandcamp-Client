package com.wonderfulrobot.BandCampAPILibraryExample.test;

import java.util.List;

import junit.framework.TestCase;

import com.wonderfulrobot.bandcampapi.core.BandcampConstants;
import com.wonderfulrobot.bandcampapi.core.BandcampException;
import com.wonderfulrobot.bandcampapi.core.BandcampService;
import com.wonderfulrobot.bandcampapi.core.BandcampServiceFactory;
import com.wonderfulrobot.bandcampapi.data.Album;
import com.wonderfulrobot.bandcampapi.data.Band;
import com.wonderfulrobot.bandcampapi.data.DataConstants;
import com.wonderfulrobot.bandcampapi.data.DiscographyItem;
import com.wonderfulrobot.bandcampapi.data.SearchResult;
import com.wonderfulrobot.bandcampapi.data.Track;

public class TestBandcampService extends TestCase {
	
	private BandcampService mService;
	
	private long session_id;

	public TestBandcampService() {
	}

	public TestBandcampService(String name) {
		super(name);
	}
	
	public void setUp(){
		this.mService = new BandcampServiceFactory().buildService(TestConstants.key, TestConstants.secret);
	}
	

	
	public void testSearch(){
		try {
			SearchResult sr = mService.search("Sola Rosa");
			assertNotNull(sr);
			
			assertEquals(sr.getBands().size(), 2);
			

			assertEquals(""+sr.getBands().get(0).getAttribute(BandcampConstants.BAND_ID), "1550132144");
			assertEquals(""+sr.getBands().get(1).getAttribute(BandcampConstants.BAND_ID), "2248292948");
			
		} catch (BandcampException e) {
			fail();
			e.printStackTrace();
		}

	}
	
	public void testSearchEmpty(){
		try {
			SearchResult sr = mService.search("");			
			assertNotNull(sr);
			

			assertNull(sr.getBands());
			
		} catch (BandcampException e) {
			assertNotNull(e);
			e.printStackTrace();
		}
	}
	
	public void testSearchNoResult(){
		try {
			SearchResult sr = mService.search("notanartistname");			
			assertNotNull(sr);

			assertEquals(sr.getBands().size(), 0);
		} catch (BandcampException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	public void testDiscography(){
		try {
			List<DiscographyItem> dItems = mService.getDiscography(new long[]{203035041L});
			assertNotNull(dItems);
			
			assertEquals(dItems.size(), 9);
			

			
		} catch (BandcampException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	public void testDiscographyMulti(){
		try {
			List<DiscographyItem> dItems = mService.getDiscography(new long[]{3463798201L,203035041L});
			assertNotNull(dItems);
			
			assertEquals(dItems.size(), 23);
		} catch (BandcampException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testDiscographyEmpty(){
		try {
			List<DiscographyItem> dItems = mService.getDiscography(new long[]{});

		} catch (BandcampException e) {
			e.printStackTrace();
			assertNotNull(e);
		}
	}

	public void testBand(){
		try {
			Band band = mService.getBand(3463798201L);
			assertNotNull(band);
			
			assertEquals(band.getAttribute(DataConstants.BAND_NAME),"Amanda Palmer");
			assertEquals(band.getAttribute(DataConstants.BAND_SUBDOMAIN),"amandapalmer");
			assertEquals(band.getAttribute(DataConstants.BAND_URL),"http://music.amandapalmer.net");
		} catch (BandcampException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testBandNone(){
		try {
			Band band = mService.getBand(-1L);
			assertNotNull(band);

			assertEquals(band.getAttribute(DataConstants.BAND_NAME),null);
			assertEquals(band.getAttribute(DataConstants.BAND_SUBDOMAIN),null);
			assertEquals(band.getAttribute(DataConstants.BAND_URL),null);
			
			
		} catch (BandcampException e) {
			e.printStackTrace();
			assertNotNull(e);
		}
	}
	
	public void testAlbum(){
		
		try {
			Album album = mService.getAlbum(2587417518L);
			assertNotNull(album);

			assertEquals(album.getAttribute(DataConstants.ALBUM_TITLE),"Who Killed Amanda Palmer");
			assertEquals(album.getAttribute(DataConstants.ALBUM_RELEASE_DATE),1221523200);
			assertEquals(album.getAttribute(DataConstants.ALBUM_SMALL_ART_URL),"http://f.bandcamp.com/z/33/09/3309055932-1.jpg");
			
			assertNotNull(album.getTracks());

			assertEquals(album.getTracks().size(),12);
			
		} catch (BandcampException e) {
			fail();
			e.printStackTrace();
		}
		
		
		
	}
	
	public void testAlbumNone(){
		
		try {
			Album album = mService.getAlbum(-1L);
			assertNotNull(album);

			assertEquals(album.getAttribute(DataConstants.ALBUM_TITLE),null);
			assertEquals(album.getAttribute(DataConstants.ALBUM_RELEASE_DATE),null);
			assertEquals(album.getAttribute(DataConstants.ALBUM_SMALL_ART_URL),null);
			
			assertNull(album.getTracks());

			
		} catch (BandcampException e) {
			assertNotNull(e);
			e.printStackTrace();
		}
	}
	
	public void testTrack(){
		try {
			Track track = mService.getTrack(1269403107L);
			assertNotNull(track);
			

			assertEquals(track.getAttribute(DataConstants.TRACK_TITLE),"Creep (Live in Prague)");
			
			//check for 12 tracks
		} catch (BandcampException e) {
			e.printStackTrace();
		}
	}
	
	public void testTrackNone(){
		try {
			Track track = mService.getTrack(-1L);
			
			assertNotNull(track);
			assertEquals(track.getAttribute(DataConstants.TRACK_TITLE),null);
			//check for 12 tracks
		} catch (BandcampException e) {
			e.printStackTrace();
		}
	}

}
