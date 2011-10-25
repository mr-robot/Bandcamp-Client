package com.wonderfulrobot.bandcampapi.data.impl;

import com.wonderfulrobot.bandcampapi.data.DataConstants;
import com.wonderfulrobot.bandcampapi.data.Track;

public class TrackImpl extends Track {
	
	@Override
	public String getTitle(){
		return (String) getAttribute(DataConstants.TRACK_TITLE);
	}
		
	@Override
	public String getStreamURL(){
		return (String) getAttribute(DataConstants.TRACK_STREAM_URL);
	}
	
	@Override
	public String toString(){
		return this.getTitle();
	}

	@Override
	public long getTrackId() {
		return Long.parseLong(""+getAttribute(DataConstants.TRACK_ID));
	}

	@Override
	public int getNumber() {
		if(getAttribute(DataConstants.TRACK_NUMBER) != null)
			return (Integer)getAttribute(DataConstants.TRACK_NUMBER);
		else
			return -1;
	}
	
	@Override
	public double getDuration() {
		return (Double) getAttribute(DataConstants.TRACK_DURATION);
	}

	@Override
	public String getAlbumTitle() {
		return (String) getAttribute(DataConstants.ALBUM_TITLE);
	}

	@Override
	public String getBandTitle() {
		return (String) getAttribute(DataConstants.BAND_NAME);
	}

	@Override
	public String getArtUrl() {
		return (String) getAttribute(DataConstants.ALBUM_LARGE_ART_URL);
	}

	@Override
	public void update(Track result) {
		this.setAttributes(result.getAttributes());
	}

	@Override
	public String getURL() {
		return (String) getAttribute(DataConstants.TRACK_URL);
	}

}
