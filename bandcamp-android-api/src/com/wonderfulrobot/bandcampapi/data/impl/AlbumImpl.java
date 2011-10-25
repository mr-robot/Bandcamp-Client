package com.wonderfulrobot.bandcampapi.data.impl;

import java.util.List;

import com.wonderfulrobot.bandcampapi.data.Album;
import com.wonderfulrobot.bandcampapi.data.DataConstants;
import com.wonderfulrobot.bandcampapi.data.Track;

public class AlbumImpl extends Album {
	
	@Override
	public List<Track> getTracks() {
		return tracks;
	}

	private List<Track> tracks;
	
	@Override
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	@Override
	public String getTitle(){
		return (String) getAttribute(DataConstants.ALBUM_TITLE);
	}
	
	@Override
	public String getArtURL(){
		return (String) getAttribute(DataConstants.ALBUM_SMALL_ART_URL);
	}
	

	@Override
	public long getAlbumId() {
		return Long.parseLong(""+getAttribute(DataConstants.ALBUM_ID));
	}
	

	@Override
	public String getBandTitle() {
		return "";
	}

	@Override
	public void update(Album album) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getURL() {
		return (String) getAttribute(DataConstants.ALBUM_URL);
	}
	

}
