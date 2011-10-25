package com.wonderfulrobot.bandcampapi.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wonderfulrobot.bandcampapi.core.BandcampException;
import com.wonderfulrobot.bandcampapi.data.Album;
import com.wonderfulrobot.bandcampapi.data.Band;
import com.wonderfulrobot.bandcampapi.data.DataConstants;
import com.wonderfulrobot.bandcampapi.data.DiscographyItem;
import com.wonderfulrobot.bandcampapi.data.GenericElement;
import com.wonderfulrobot.bandcampapi.data.SearchResult;
import com.wonderfulrobot.bandcampapi.data.Track;
import com.wonderfulrobot.bandcampapi.data.impl.AlbumImpl;
import com.wonderfulrobot.bandcampapi.data.impl.TrackImpl;

public class JSONDeserialiser {
	
	/**
	 * 
	 * Deserialises JSON Objects into more manageable Java Objects for easier manipulation
	 * 

	 */
	
	/**
	 * 
	 * @param object JSON Object
	 * @return
	 * @throws JSONException
	 */
	
	public static List<DiscographyItem> deserialiseDiscography(JSONObject object) throws JSONException{
		List<DiscographyItem> discoItems = new ArrayList<DiscographyItem>();
		
		if(object.has(DataConstants.DISCOGRAPHY)){
			
			JSONArray jsonDiscoItems = object.getJSONArray(DataConstants.DISCOGRAPHY);
			
			for(int i = 0; i < jsonDiscoItems.length(); i++){
				DiscographyItem discoItem = new DiscographyItem();
				deserialise(jsonDiscoItems.getJSONObject(i), discoItem);
				discoItems.add(discoItem);
			}
		}else{
			//We've Got a Dictionary of Artist Discos
			JSONArray keys = object.names();
			for(int j = 0; j < keys.length(); j++){
				String artist_id = keys.getString(j);
				if(object.has(artist_id)){
					List<DiscographyItem> discoItemsResult = deserialiseDiscography(object.getJSONObject(artist_id));
					discoItems.addAll(discoItemsResult);
				}
			}
		}
		return discoItems;
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static SearchResult deserialiseSearch(JSONObject object) throws JSONException{
		SearchResult result = new SearchResult();
		
		if(object.has(DataConstants.RESULTS)){
			
			JSONArray jsonResultItems = object.getJSONArray(DataConstants.RESULTS);

			List<Band> bands = new ArrayList<Band>();
			for(int i = 0; i < jsonResultItems.length(); i++){
				bands.add(deserialiseBand(jsonResultItems.getJSONObject(i)));
			}
			result.setBands(bands);
		}
		return result;
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static Band deserialiseBand(JSONObject object) throws JSONException{
		Band b = new Band();
		deserialise(object, b);
		return b;
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static Track deserialiseTrack(JSONObject object) throws JSONException{
		TrackImpl t = new TrackImpl();
		deserialise(object, t);
		return t;
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static Album deserialiseAlbum(JSONObject object) throws JSONException{
		AlbumImpl a = new AlbumImpl();
		
		deserialise(object, a);
		
		if(object.has(DataConstants.TRACKS)){
			
			JSONArray jsonTracks = object.getJSONArray(DataConstants.TRACKS);
			List<Track> tracks = new ArrayList<Track>();
			for(int i = 0; i < jsonTracks.length(); i++){
				tracks.add(deserialiseTrack(jsonTracks.getJSONObject(i)));
			}
			a.setTracks(tracks);
		}
		
		return a;
	}
	
	/**
	 * 
	 * @param object
	 * @param element
	 * @throws JSONException
	 */
	public static void deserialise(JSONObject object, GenericElement element) throws JSONException{
	    @SuppressWarnings("unchecked")
		Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){
		  currentKey =  keys.next();

		  element.setAttribute(currentKey, object.get(currentKey));
	      
	    }
	}
	
	public static void checkError(JSONObject object) throws JSONException, BandcampException{
		if(object.has(DataConstants.ERROR)){
			if(object.has(DataConstants.ERROR_MESSAGE))
				throw new BandcampException(object.getString(DataConstants.ERROR_MESSAGE));
			else
				throw new BandcampException("Error");
		}
	}

}
