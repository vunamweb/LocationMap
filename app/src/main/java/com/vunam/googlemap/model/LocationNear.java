package com.vunam.googlemap.model;

import java.util.List;

public class LocationNear {

	private String name;
	private String user_ratings_total;
	private String rating;
	private List<Photo> photos;
	private Geometry geometry;

	public LocationNear() {

	}

	public LocationNear(String name) {
		this.name = name;
	}

	public LocationNear(String name, List<Photo> photos) {
		this.name = name;
		this.photos = photos;
	}

	public LocationNear(String name, String user_ratings_total, String rating, List<Photo> photos) {
		this.name = name;
		this.user_ratings_total = user_ratings_total;
		this.rating = rating;
		this.photos = photos;
	}

	public LocationNear(String name, String user_ratings_total, String rating, List<Photo> photos, Geometry geometry) {
		this.name = name;
		this.user_ratings_total = user_ratings_total;
		this.rating = rating;
		this.photos = photos;
		this.geometry = geometry;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getUser_ratings_total() {
		return user_ratings_total;
	}

	public void setUser_ratings_total(String user_ratings_total) {
		this.user_ratings_total = user_ratings_total;
	}

	public String getRating() {
		return (rating != null) ? rating : "0";
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
}
