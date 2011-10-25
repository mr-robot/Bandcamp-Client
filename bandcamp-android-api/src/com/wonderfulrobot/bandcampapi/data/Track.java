package com.wonderfulrobot.bandcampapi.data;

public abstract class Track extends GenericElement {

	public Track() {
		super();
	}

	public abstract double getDuration();

	public abstract int getNumber();

	public abstract long getTrackId();

	public abstract String toString();

	public abstract String getStreamURL();

	public abstract String getTitle();

	public abstract String getAlbumTitle();
	
	public abstract String getBandTitle();
	
	public abstract String getArtUrl();

	public abstract void update(Track result);

	public abstract String getURL();
}