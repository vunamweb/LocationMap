package com.vunam.googlemap;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.vunam.googlemap.fragment.IconNear;
import com.vunam.googlemap.fragment.InterfaceFragment;
import com.vunam.googlemap.fragment.ListItemHorizontal;
import com.vunam.googlemap.fragment.ListItemVertical;
import com.vunam.googlemap.network.GetResponse;
import com.vunam.googlemap.service.DirectionMap.DirectionMapService;
import com.vunam.googlemap.service.ShowMap.ShowMapHorizontalService;
import com.vunam.googlemap.service.ShowMap.ShowMapVerticalService;
import com.vunam.mylibrary.GoogleMap.GoogleMapBasic;
import com.vunam.mylibrary.utils.Android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, InterfaceFragment {
	private GoogleMap mMap;
	private ProgressDialog mDialog;
    protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	static public double currentLatitude;
	static public double currentLongitude;
	static public JSONArray listLocationNear;
	@BindView(R.id.spiner_menu) Spinner spinerTypeMap;
	@BindView(R.id.progressBarMap) ProgressBar progressBarMap;

	public ProgressBar getProgressBarMap() {
		return progressBarMap;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		ButterKnife.bind(this);
//		Android.MyProgressDialog.getInstance(MapsActivity.this)
//				.setTitle("Loading")
//				.setMessage("Loading...");
		//mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//if(true){
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{
							Manifest.permission.ACCESS_FINE_LOCATION,
							Manifest.permission.ACCESS_COARSE_LOCATION},
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

		String[] typemaps = getResources().getStringArray(R.array.typemaps);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typemaps, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinerTypeMap.setAdapter(adapter);
		spinerTypeMap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				switch (i) {
					case 0:
						GoogleMapBasic.getInstance(mMap, MapsActivity.this).setTypeMap(GoogleMap.MAP_TYPE_NORMAL);
						break;
					case 1:
						GoogleMapBasic.getInstance(mMap, MapsActivity.this).setTypeMap(GoogleMap.MAP_TYPE_HYBRID);
						break;
					case 2:
						GoogleMapBasic.getInstance(mMap, MapsActivity.this).setTypeMap(GoogleMap.MAP_TYPE_SATELLITE);
						break;
					case 3:
						GoogleMapBasic.getInstance(mMap, MapsActivity.this).setTypeMap(GoogleMap.MAP_TYPE_TERRAIN);
						break;
					case 4:
						GoogleMapBasic.getInstance(mMap, MapsActivity.this).setTypeMap(GoogleMap.MAP_TYPE_NONE);
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		//Android.showDialog(this);
// Begin the transaction
		Android.transactionFragment(this, R.id.bootom_sheet, new IconNear());
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
//		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//			ActivityCompat.requestPermissions(this, new String[]{
//							Manifest.permission.ACCESS_FINE_LOCATION,
//							Manifest.permission.ACCESS_COARSE_LOCATION},
//					1);
//		}
		GoogleMapBasic.getInstance(mMap,this).setMyLocationEnable(true);
		//GoogleMapBasic.getInstance(mMap,context).setTypeMap(GoogleMap.MAP_TYPE_SATELLITE);
		//mMap.setMyLocationEnabled(true);
		LatLng positionCurrent = new LatLng(currentLatitude, currentLongitude);
		GoogleMapBasic.getInstance(mMap,this).addMarker(positionCurrent,"location");
		//mMap.addMarker(new MarkerOptions().position(positionCurrent).title("my location"));
		GoogleMapBasic.getInstance(mMap,this).animateCamera(positionCurrent,17.0f);
		//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(positionCurrent, 17.0f));
	}

	@Override
    public void onLocationChanged(Location location) {
//        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
////		LatLng sydney1 = new LatLng(106.6024487, 10.8653133);
////		LatLng sydney2 = new LatLng(106.6044188, 10.8656337);
//
//		mMap.clear();
//        mMap.addMarker(new MarkerOptions().position(sydney).title("my location"));
//		//mMap.addMarker(new MarkerOptions().position(sydney1).title("my location"));
//		//mMap.addMarker(new MarkerOptions().position(sydney2).title("my location"));
//
//
//		//mMap.setMyLocationEnabled(true);
//        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f));
//        Log.i("show","dfff");
    }

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("Latitude", "disable");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("Latitude", "enable");
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
	public void phoneCall(String phoneNumber) {
		Android.startActivityPhoneCall(MapsActivity.this,"+8423466566");
	}

	@Override
	public void showListVertical() {
//		Android.MyProgressDialog.getInstance(MapsActivity.this).show();
		Android.MyProgressBar.getInstance(MapsActivity.this,progressBarMap).show();
		new ShowMapVerticalService(MapsActivity.this, mMap).execute();
	}

	@Override
	public void showDirection(final String originLat, final String originLng, final String desLat, final String desLng, final String mode) {
		new DirectionMapService(MapsActivity.this, mMap)
				.setOriginLat(originLat)
				.setOriginLng(originLng)
				.setdesLat(desLat)
				.setdesLng(desLng)
				.setMode("driving")
				.execute();
	}

	@Override
	public void showListHorizontal() {
		new ShowMapHorizontalService(MapsActivity.this,mMap).execute();
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
