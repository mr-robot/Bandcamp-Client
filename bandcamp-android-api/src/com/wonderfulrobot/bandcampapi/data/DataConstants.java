package com.wonderfulrobot.bandcampapi.data;

public class DataConstants {
	
	/**
	 * 
	 * Set of Constants to retrieve fields from Bandcamp Objects
	 * 
	 * 
	 */

	//Elements
	public static final String BAND = "band";
	public static final String ALBUM = "album";
	public static final String TRACK = "track";
	public static final String DISCOGRAPHY = "discography";
	
	//Response

	public static final String SMALL_ART_URL = "small_art_url";
	//Track
	public static final String TRACK_ID = "track_id";
	public static final String TRACK_TITLE = "title";
	public static final String TRACK_STREAM_URL = "streaming_url";
	public static final String TRACK_LYRICS = "lyrics";
	public static final String TRACK_NUMBER = "number";
	public static final String TRACK_DURATION = "duration";
	public static final String TRACK_URL = "url";
	
	//Album
	public static final String ALBUM_ID = "album_id";
	public static final String ALBUM_TITLE = "title";
	public static final String ALBUM_ABOUT = "about";
	public static final String ALBUM_CREDITS = "credits";
	public static final String ALBUM_RELEASE_DATE = "release_date";
	public static final String ALBUM_SMALL_ART_URL = "small_art_url";
	public static final String ALBUM_LARGE_ART_URL = "large_art_url";
	public static final String ALBUM_URL = "url";

	//Band
	public static final String BAND_ID = "band_id";
	public static final String BAND_NAME = "name";
	public static final String BAND_SUBDOMAIN = "subdomain";
	public static final String BAND_URL = "url";
	public static final String BAND_OFFSITE_URL = "offsite_url";
	
	
	
	//SearchResult
	
	//Discography
	public static final int DISCO_ALBUM = 2;
	public static final int DISCO_TRACK = 1;
	public static final String DISCO_TITLE = "title";
	public static final String DISCO_ARTIST = "artist";
	public static final String DISCO_URL = "url";
	public static final String TRACKS = "tracks";
	public static final String RESULTS = "results";
	public static final String ERROR = "error";
	public static final String ERROR_MESSAGE = "error_message";
}
