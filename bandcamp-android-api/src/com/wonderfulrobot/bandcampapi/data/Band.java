package com.wonderfulrobot.bandcampapi.data;

public class Band extends GenericElement {

	public String getName(){
		return (String) getAttribute(DataConstants.BAND_NAME);
	}
}
