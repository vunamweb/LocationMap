package com.vunam.googlemap.model;

public class Geometry {
	private LocationMap location;

	public Geometry() {
	}

	public Geometry(LocationMap location) {
		this.location = location;
	}

	public LocationMap getLocation() {
		return location;
	}

	public void setLocation(LocationMap location) {
		this.location = location;
	}
}
