package com.vunam.googlemap.model;

public class Photo {
	private String photo_reference;
	private String height;
	private String width;

	public Photo(String photo_reference, String height, String width) {
		this.photo_reference = photo_reference;
		this.height = height;
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public String getWidth() {
		return width;
	}

	public void setHeight(String height) {

		this.height = height;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Photo() {
	}

	public Photo(String photo_reference) {
		this.photo_reference = photo_reference;
	}

	public String getPhoto_reference() {
		return photo_reference;
	}

	public void setPhoto_reference(String photo_reference) {
		this.photo_reference = photo_reference;
	}
}
