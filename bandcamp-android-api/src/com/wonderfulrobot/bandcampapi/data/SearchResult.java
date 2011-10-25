package com.wonderfulrobot.bandcampapi.data;

import java.util.List;

public class SearchResult extends GenericElement {

	private List<Band> bands;

	public List<Band> getBands() {
		return bands;
	}

	public void setBands(List<Band> bands) {
		this.bands = bands;
	}
}
