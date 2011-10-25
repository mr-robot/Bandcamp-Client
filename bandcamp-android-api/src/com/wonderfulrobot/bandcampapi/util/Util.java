package com.wonderfulrobot.bandcampapi.util;

import com.wonderfulrobot.bandcampapi.data.Band;
import com.wonderfulrobot.bandcampapi.data.DataConstants;
import com.wonderfulrobot.bandcampapi.data.SearchResult;

public class Util {

	/**
	 * 
	 * Retrieves all of the Band Id's returned from a Search Result
	 * To be used to retrieve Discography
	 * 
	 * @param result
	 * @return
	 */
	public static long[] getBandIds(SearchResult result){
		int i = 0;
		long[] ids = new long[result.getBands().size()];
		
		for(Band b : result.getBands()){
			ids[i] = Long.parseLong(""+  b.getAttribute(DataConstants.BAND_ID));
			i++;
		}
		return ids;
	}
}
