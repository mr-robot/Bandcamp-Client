package com.wonderfulrobot.bandcampapi.data;

public class DiscographyItem extends GenericElement {
	
	public int getType() {
		return type;
	}

	private int type;
	
	public String getTitle(){
		return (String) getAttribute(DataConstants.DISCO_TITLE);
	}

	public String getArtURL(){
		return (String) getAttribute(DataConstants.ALBUM_SMALL_ART_URL);
	}
	

	public long getId() {
		if(hasAttribute(DataConstants.ALBUM_ID))
			return Long.parseLong(""+getAttribute(DataConstants.ALBUM_ID));
		else if(hasAttribute(DataConstants.TRACK_ID))
			return Long.parseLong(""+getAttribute(DataConstants.TRACK_ID));
		else
			return -1;
	}

	public String getURL() {
		return (String) getAttribute(DataConstants.DISCO_URL);
	}
	
	public String toString(){
		return (String)getAttribute(DataConstants.DISCO_TITLE);
	}

}
