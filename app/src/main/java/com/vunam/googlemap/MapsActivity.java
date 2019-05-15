package com.vunam.googlemap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vunam.googlemap.fragment.IconNear;
import com.vunam.googlemap.fragment.ItemFragment;
import com.vunam.googlemap.network.GetResponse;
import com.vunam.googlemap.service.GetMap;
import com.vunam.mylibrary.utils.Android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener ,IconNear.OnFragmentInteractionListener,ItemFragment.OnListFragmentInteractionListener {

    private GoogleMap mMap;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    double currentLatitude;
    double currentLongitude;
    public JSONArray listLocationNear;
    //@BindView(R.id.imageViewRestaurant) ImageView mButtonShowBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
		ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //if(true){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20, 0, this);
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            currentLatitude = myLocation.getLatitude();
            currentLongitude = myLocation.getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Begin the transaction
        Android.transactionFragment(this,R.id.bootom_sheet,new IconNear());
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		// Replace the contents of the container with the new fragment
//		ft.replace(R.id.bootom_sheet, new IconNear());
//		ft.commit();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

         //Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    public void onLocationChanged(Location location) {
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//		LatLng sydney1 = new LatLng(106.6024487, 10.8653133);
//		LatLng sydney2 = new LatLng(106.6044188, 10.8656337);

		mMap.clear();
        mMap.addMarker(new MarkerOptions().position(sydney).title("my location"));
		//mMap.addMarker(new MarkerOptions().position(sydney1).title("my location"));
		//mMap.addMarker(new MarkerOptions().position(sydney2).title("my location"));


		//mMap.setMyLocationEnabled(true);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f));
        Log.i("show","dfff");
    }


    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
	public void show() {
		new GetMap() {
            @Override
            public Object getBackground() {
                Object response = null;
                Object response1 = null;
                //String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=cruise&key=AddYourOwnKeyHere";
                String url = getResources().getString(R.string.url_near_map);
				//String url_place_detail = getResources().getString(R.string.url_place_detail);
                String apiKey = getResources().getString(R.string.google_maps_key);

				url = url + "?location=" + currentLatitude + "," + currentLongitude + "&radius=500&types=restaurant&key=" + apiKey;

				try {
                    response = new GetResponse(getApplicationContext(), url).getResponse(null);
					JSONObject jsonObject = (JSONObject) response;
					JSONArray jsonArray = jsonObject.optJSONArray("results");
					for(int i=0;i<jsonArray.length();i++){
						JSONObject object = jsonArray.getJSONObject(i);
						String placeId = object.getString("place_id");
						String url_place_detail = getResources().getString(R.string.url_place_detail) + "?placeid=" + placeId + "&key=" + apiKey;
						try {
							response1 = new GetResponse(getApplicationContext(), url_place_detail).getResponse(null);
							JSONArray listPhoto = ((JSONObject) response1).getJSONObject("result").getJSONArray("photos");
							object.put("list_photo", listPhoto);
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
            public void updateGUI(Object result)
            {
                JSONObject jsonObject = (JSONObject) result;
                JSONArray jsonArray = jsonObject.optJSONArray("results");

                listLocationNear = jsonArray;
                double latitude ,longitude;

                //add market
                for(int i=0;i<jsonArray.length();i++)
                {
                    try {
                        latitude = jsonArray.getJSONObject(i).optJSONObject("geometry").optJSONObject("location").getDouble("lat");
                        longitude = jsonArray.getJSONObject(i).optJSONObject("geometry").optJSONObject("location").getDouble("lng");
                        LatLng sydney = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("my location"));
                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f));
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

				Android.transactionFragment(MapsActivity.this, R.id.bootom_sheet, new ItemFragment());

            }

        }.execute();

		// Begin the transaction
//        Android.transactionFragment(this, R.id.bootom_sheet, new ItemFragment());
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		// Replace the contents of the container with the new fragment
//		ft.replace(R.id.bootom_sheet, new ItemFragment());
//// or ft.add(R.id.your_placeholder, new FooFragment());
//// Complete the changes added above
//		ft.commit();
		}


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
