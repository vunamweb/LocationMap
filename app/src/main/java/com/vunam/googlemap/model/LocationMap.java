package com.vunam.googlemap.model;

public class LocationMap {
	private double lng;
	private double lat;

	public LocationMap() {
	}

	public LocationMap(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}
}
