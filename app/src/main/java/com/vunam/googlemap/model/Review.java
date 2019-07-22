package com.vunam.googlemap.model;

public class Review {
	private String profile_photo_url;
	private String author_name;
	private String rating;
	private String relative_time_description;
	private String text;

	public Review() {
	}

	public Review(String profile_photo_url, String author_name, String rating, String relative_time_description, String text) {
		this.profile_photo_url = profile_photo_url;
		this.author_name = author_name;
		this.rating = rating;
		this.relative_time_description = relative_time_description;
		this.text = text;
	}

	public String getProfile_photo_url() {
		return profile_photo_url;
	}

	public void setProfile_photo_url(String profile_photo_url) {
		this.profile_photo_url = profile_photo_url;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRelative_time_description() {
		return relative_time_description;
	}

	public void setRelative_time_description(String relative_time_description) {
		this.relative_time_description = relative_time_description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
