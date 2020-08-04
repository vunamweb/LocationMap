package com.vunam.googlemap.service.ShowMap;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.vunam.googlemap.MapsActivity;
import com.vunam.googlemap.R;
import com.vunam.googlemap.fragment.ListItemHorizontal;
import com.vunam.googlemap.fragment.ListItemVertical;
import com.vunam.googlemap.network.GetResponse;
import com.vunam.mylibrary.GoogleMap.GoogleMapBasic;
import com.vunam.mylibrary.multithreading.ProcessAsyncTask;
import com.vunam.mylibrary.utils.Android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapService {
	Context context;
	GoogleMap mMap;
	int iconMarker;

	public MapService(Context context, GoogleMap mMap) {
		this.context = context;
		this.mMap = mMap;
	}

	public MapService setTypeMap(int iconMarker) {
		this.iconMarker = iconMarker;
		return this;
	}

	public String getTypemap() {
		switch (iconMarker) {
			case R.drawable.coffee_marker:
				return "cafe";
			case R.drawable.park_marker:
				return "park";

			case R.drawable.karaoke_marker:
				return "night_club";

			case R.drawable.atm_marker:
				return "atm";

			case R.drawable.airport_marker:
				return "airport";

			default:
				return "restaurant";
		}
	}

	public void ShowMapVertical() {
		final String typeMap = getTypemap();
		new ProcessAsyncTask() {
			@Override
			public Object getBackground() {
				Object response = null;
				Object response1 = null;
				//String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=cruise&key=AddYourOwnKeyHere";
				String url = context.getResources().getString(R.string.url_near_map);
				//String url_place_detail = getResources().getString(R.string.url_place_detail);
			   String apiKey = context.getResources().getString(R.string.google_maps_key);

			   url = url + "?location=" + MapsActivity.currentLatitude + "," + MapsActivity.currentLongitude + "&radius=500&types=" + typeMap + "&key=" + apiKey;

			   try {
				   response = new GetResponse(context.getApplicationContext(), url).getResponse(null);
				   JSONObject jsonObject = (JSONObject) response;
				   JSONArray jsonArray = jsonObject.optJSONArray("results");
				   for (int i = 0; i < jsonArray.length(); i++) {
					   JSONObject object = jsonArray.getJSONObject(i);
					   String placeId = object.getString("place_id");
					   String url_place_detail = context.getResources().getString(R.string.url_place_detail) + "?placeid=" + placeId + "&key=" + apiKey;
					   try {
						   response1 = new GetResponse(context.getApplicationContext(), url_place_detail).getResponse(null);
						   if (((JSONObject) response1).has("result"))
							   //jsonArray.remove(i);
							   jsonArray.put(i, ((JSONObject) response1).optJSONObject("result"));
						   //JSONArray listPhoto = ((JSONObject) response1).getJSONObject("result").getJSONArray("photos");
						   //object = ((JSONObject) response1).optJSONObject("result");
						   //object.put("list_photo", listPhoto);
						   //object.put("test","nambu");
					   } catch (Exception e) {
						   e.printStackTrace();
					   }
				   }

			   } catch (JSONException e) {
				   e.printStackTrace();
			   }
			   return response;
		   }

		   @Override
		   public void updateGUI(Object result) {
			   double latitude, longitude;

			   JSONObject jsonObject = (JSONObject) result;
			   JSONArray jsonArray = jsonObject.optJSONArray("results");

			   ProgressBar progressBar = ((MapsActivity) context).getProgressBarMap();
			   Android.MyProgressBar.getInstance(context, progressBar).hide();

			   MapsActivity.listLocationNear = jsonArray;
			   mMap.clear();

//add market
			   for (int i = 0; i < jsonArray.length(); i++) {
				   try {
					   //Log.i("mapz",String.valueOf(i));
					   latitude = jsonArray.getJSONObject(i).optJSONObject("geometry").optJSONObject("location").getDouble("lat");
					   longitude = jsonArray.getJSONObject(i).optJSONObject("geometry").optJSONObject("location").getDouble("lng");
					   final String title = jsonArray.getJSONObject(i).getString("name");
					   final String imageUrl = jsonArray.getJSONObject(i).getString("icon");
					   final LatLng position = new LatLng(latitude, longitude);

					   MapsActivity.listMarker.add(GoogleMapBasic.getInstance(mMap, context).addMarker(position, title,iconMarker));
					   //mMap.addMarker(new MarkerOptions().position(sydney).title("my location"));
					   //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f));
				   } catch (JSONException e) {
					   e.printStackTrace();
					   Log.e("error", String.valueOf(i));
				   }

			   }
			   Android.transactionFragment((MapsActivity) context, R.id.bootom_sheet, new ListItemVertical());
			   //MapsActivity.listMarker.get(5).showInfoWindow();
		   }
	   }.execute();
	}

	public void ShowMapHorizontal() {
		new ProcessAsyncTask() {

			@Override
			public Object getBackground() {
				return null;
			}

			@Override
			public void updateGUI(Object result) {
				Android.transactionFragment((MapsActivity) context, R.id.bootom_sheet, new ListItemHorizontal());
			}
		}.execute();
	}
}
