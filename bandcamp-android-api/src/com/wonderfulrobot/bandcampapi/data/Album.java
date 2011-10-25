package com.wonderfulrobot.bandcampapi.data;

import java.util.List;

public abstract class Album extends GenericElement {

	public Album() {
		super();
	}
	public abstract void update(Album album);

	public abstract long getAlbumId();

	public abstract String getArtURL();

	public abstract String getTitle();
	
	public abstract String getBandTitle();

	public abstract void setTracks(List<Track> tracks);

	public abstract List<Track> getTracks();
	public abstract String getURL() ;

}