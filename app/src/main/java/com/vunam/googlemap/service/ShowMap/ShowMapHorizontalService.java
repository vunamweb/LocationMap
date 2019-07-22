package com.vunam.googlemap.service.ShowMap;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.vunam.googlemap.MapsActivity;
import com.vunam.googlemap.R;
import com.vunam.googlemap.fragment.ListItemHorizontal;
import com.vunam.mylibrary.multithreading.ProcessAsyncTask;
import com.vunam.mylibrary.utils.Android;

public class ShowMapHorizontalService extends ProcessAsyncTask {
	Context context;
	GoogleMap mMap;

	public ShowMapHorizontalService(Context context, GoogleMap mMap) {
		this.context = context;
		this.mMap = mMap;
	}

	@Override
	public Object getBackground() {
		return null;
	}

	@Override
	public void updateGUI(Object result) {
		Android.transactionFragment((MapsActivity) context, R.id.bootom_sheet, new ListItemHorizontal());
	}
}
