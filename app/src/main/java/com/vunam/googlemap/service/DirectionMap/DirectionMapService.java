package com.vunam.googlemap.service.DirectionMap;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vunam.googlemap.R;
import com.vunam.googlemap.network.GetResponse;
import com.vunam.mylibrary.multithreading.ProcessAsyncTask;
import com.vunam.mylibrary.utils.Android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionMapService extends ProcessAsyncTask {
	Context context;
	GoogleMap mMap;
	String originLat;
	String originLng;
	 String desLat;
	 String desLng;
	 String mode;

	public DirectionMapService(Context context, GoogleMap mMap) {
		this.context = context;
		this.mMap = mMap;
	}

	public DirectionMapService setOriginLat(String originLat) {
		this.originLat = originLat;
		return this;
	}

	public DirectionMapService setOriginLng(String originLng) {
		this.originLng = originLng;
		return this;
	}

	public DirectionMapService setdesLat(String desLat) {
		this.desLat = desLat;
		return this;
	}

	public DirectionMapService setdesLng(String desLng) {
		this.desLng = desLng;
		return this;
	}

	public DirectionMapService setMode(String mode) {
		this.mode = mode;
		return this;
	}

@Override
	public Object getBackground() {
		Object response = null;
		Object response1 = null;
		//String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=cruise&key=AddYourOwnKeyHere";
		String url = context.getResources().getString(R.string.url_direction);
		//String url_place_detail = getResources().getString(R.string.url_place_detail);
		String apiKey = context.getResources().getString(R.string.google_maps_key);

		url = url + "?origin=" + originLat + "," + originLng + "&destination=" + desLat + "," +
				desLng + "&mode=" + mode + "&key=" + apiKey;

		try {
			response = new GetResponse(context.getApplicationContext(), url).getResponse(null);
			return Android.DirectionsJSONParser.parse((JSONObject) response);
} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public void updateGUI(Object result) {
		List<List<HashMap<String, String>>> routers = new ArrayList<List<HashMap<String, String>>>();
		routers = (List<List<HashMap<String, String>>>) result;
		ArrayList points = null;
		PolylineOptions lineOptions = null;
		MarkerOptions markerOptions = new MarkerOptions();

		for (int i = 0; i < routers.size(); i++) {
			points = new ArrayList();
			lineOptions = new PolylineOptions();

			List<HashMap<String, String>> path = routers.get(i);

			for (int j = 0; j < path.size(); j++) {
				HashMap<String, String> point = path.get(j);

				double lat = Double.parseDouble(point.get("lat"));
				double lng = Double.parseDouble(point.get("lng"));
				LatLng position = new LatLng(lat, lng);

				points.add(position);
			}

			lineOptions.addAll(points);
			lineOptions.width(12);
			lineOptions.color(Color.RED);
			lineOptions.geodesic(true);

			mMap.addPolyline(lineOptions);

		}

// Drawing polyline in the Google Map for the i-th route
		//mMap.addPolyline(lineOptions);
	}
}
