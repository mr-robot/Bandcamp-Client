package com.wonderfulrobot.bandcampapi.core;

import java.util.List;

import org.json.JSONObject;

import com.wonderfulrobot.bandcampapi.data.Album;
import com.wonderfulrobot.bandcampapi.data.Band;
import com.wonderfulrobot.bandcampapi.data.DiscographyItem;
import com.wonderfulrobot.bandcampapi.data.SearchResult;
import com.wonderfulrobot.bandcampapi.data.Track;
import com.wonderfulrobot.bandcampapi.json.JSONDeserialiser;
import com.wonderfulrobot.bandcampapi.util.QueryBuilder;
import com.wonderfulrobot.bandcampapi.util.ServiceConfiguration;

public class BandcampService {
		
	private final BandcampAPI api;
	
	/**
	 * 
	 * Creates a Bandcamp Service with the given ServiceConfiguration
	 * 
	 * @param config
	 */
	public BandcampService(ServiceConfiguration config) {
		api = new BandcampAPI(config);
	}
	
	/**
	 * 
	 * @param api
	 */
	public BandcampService(BandcampAPI api) {
		this.api = api;
	}
		
	private QueryBuilder getFields(){
		return new QueryBuilder();
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws BandcampException
	 */
	public SearchResult search(String query) throws BandcampException{
		try {
			JSONObject jsonObject = api.sendRequest(BandcampConstants.BAND_SEARCH, 
													getFields().addQuery(BandcampConstants.QUERY, query).build());
			JSONDeserialiser.checkError(jsonObject);
			return JSONDeserialiser.deserialiseSearch(jsonObject);
		} catch (Exception e) {
			throw new BandcampException(e.getMessage(), e.getCause());
		}
	}
	/**
	 * 
	 * @param query
	 * @return
	 * @throws BandcampException
	 */
	public List<DiscographyItem> getDiscography(long[] query) throws BandcampException{
		try {
			/** Encode the Query as an id to include to stop the commas being encoded */
			JSONObject jsonObject = api.sendRequest(BandcampConstants.BAND_DISCO, new Object[]{}, getFields().build(),
													getFields().addAggregatedQueryObject(BandcampConstants.BAND_ID, query));
			
			JSONDeserialiser.checkError(jsonObject);
			
			return JSONDeserialiser.deserialiseDiscography(jsonObject);
		} catch (Exception e) {
			throw new BandcampException(e.getMessage(), e.getCause());
		}
	}
	/**
	 * 
	 * @param band_id
	 * @return
	 * @throws BandcampException
	 */
	public Band getBand(long band_id) throws BandcampException{
		try {
			JSONObject jsonObject = api.sendRequest(BandcampConstants.BAND_INFO, 
													getFields().addQuery(BandcampConstants.BAND_ID, band_id).build());
			JSONDeserialiser.checkError(jsonObject);
			return JSONDeserialiser.deserialiseBand(jsonObject);
		} catch (Exception e) {
			throw new BandcampException(e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 
	 * @param album_id
	 * @return
	 * @throws BandcampException
	 */
	public Album getAlbum(long album_id) throws BandcampException{
		try {
			JSONObject jsonObject = api.sendRequest(BandcampConstants.ALBUM_INFO, 
													getFields().addQuery(BandcampConstants.ALBUM_ID, album_id).build());
			JSONDeserialiser.checkError(jsonObject);
			return JSONDeserialiser.deserialiseAlbum(jsonObject);
		} catch (Exception e) {
			throw new BandcampException(e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 
	 * @param track_id
	 * @return
	 * @throws BandcampException
	 */
	public Track getTrack(long track_id) throws BandcampException{
		try {
			JSONObject jsonObject = api.sendRequest(BandcampConstants.TRACK_INFO, 
													getFields().addQuery(BandcampConstants.TRACK_ID, track_id).build());
			JSONDeserialiser.checkError(jsonObject);
			return JSONDeserialiser.deserialiseTrack(jsonObject);
		} catch (Exception e) {
			throw new BandcampException(e.getMessage(), e.getCause());
		}
	}



}
