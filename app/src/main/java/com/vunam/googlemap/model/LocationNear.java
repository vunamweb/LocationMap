package com.vunam.googlemap.model;

import java.util.List;

public class LocationNear {

	private String name;
	private List<Photo> list_photo;

	public LocationNear() {

	}

	public LocationNear(String name) {
		this.name = name;
	}

	public LocationNear(String name, List<Photo> list_photo) {
		this.name = name;
		this.list_photo = list_photo;
	}

	public List<Photo> getList_photo() {

		return list_photo;
	}

	public void setList_photo(List<Photo> list_photo) {
		this.list_photo = list_photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
