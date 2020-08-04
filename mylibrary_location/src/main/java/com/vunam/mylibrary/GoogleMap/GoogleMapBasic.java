package com.vunam.mylibrary.GoogleMap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vunam.mylibrary.LoadImg.ImgPicasso;
import com.vunam.mylibrary.R;

public class GoogleMapBasic {
   Boolean myLocationEnable;
   int typeMap;
   GoogleMap mMap;
   Context context;
   static GoogleMapBasic instance;

   private GoogleMapBasic(GoogleMap googleMap,Context context){
      this.mMap = googleMap;
      this.context = context;
   }

   public static GoogleMapBasic getInstance(GoogleMap googleMap,Context context){
      if(instance == null)
         instance = new GoogleMapBasic(googleMap,context);
      return instance;
   }

   public void setTypeMap(int typeMap){
      mMap.setMapType(typeMap);
   }

   public void setMyLocationEnable(Boolean myLocationEnable){
      if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         ActivityCompat.requestPermissions((Activity) context, new String[]{
                         Manifest.permission.ACCESS_FINE_LOCATION,
                         Manifest.permission.ACCESS_COARSE_LOCATION},
                 1);
      }
      mMap.setMyLocationEnabled(myLocationEnable);
   }

   public Marker addMarker(LatLng positionCurrent, String title, int bitmap)
   {
	   Marker marker = mMap.addMarker(new MarkerOptions().position(positionCurrent).title(title));
	   marker.setIcon(BitmapDescriptorFactory.fromResource(bitmap));
	   return marker;
   }

	public Marker addMarker(LatLng positionCurrent, String title) {
		Marker marker = mMap.addMarker(new MarkerOptions().position(positionCurrent).title(title));
		return marker;
	}

   public void animateCamera(LatLng position,Float zoom){
      mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
   }
}
