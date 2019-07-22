package com.vunam.googlemap.fragment;

import android.net.Uri;

public interface InterfaceFragment {
	void onFragmentInteraction(Uri uri);
	void showListHorizontal();
	void showListVertical();
	void showDirection(String originLat, String originLng, String desLat, String desLng, String mode);
	void phoneCall(String phoneNumber);
}
