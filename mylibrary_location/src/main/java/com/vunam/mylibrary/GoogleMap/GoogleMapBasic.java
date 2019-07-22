package com.vunam.mylibrary.GoogleMap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

   public void addMarker(LatLng positionCurrent,String title)
   {
      mMap.addMarker(new MarkerOptions().position(positionCurrent).title(title));
   }

   public void animateCamera(LatLng position,Float zoom){
      mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
   }
}
