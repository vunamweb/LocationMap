package com.vunam.googlemap.view;

import android.content.Context;

import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.vunam.googlemap.MapsActivity;
import com.vunam.mylibrary.GoogleMap.GoogleMapBasic;
import com.vunam.mylibrary.RecycleView.RecycleViewBasic;

public class RecycleViewLocationNear extends RecycleViewBasic {

	public RecycleViewLocationNear(Context context)
	{
		super(context);
	}

	public int getPositionScroll() {
		int currentHorizontalScrollOffset = recyclerView.computeHorizontalScrollOffset();
		int calculateHorizontalScrollOffset = 0;
		if(currentHorizontalScrollOffset > 300)
			Log.i("d","dd");
		int position = 0;

		for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
			for (int j = 0; j <= i; j++) {
				valueWidthPositions[j] = (recyclerView.getLayoutManager().findViewByPosition(j) != null ? recyclerView.getLayoutManager().findViewByPosition(j).getWidth() : valueWidthPositions[j]);
				calculateHorizontalScrollOffset = calculateHorizontalScrollOffset + valueWidthPositions[j];
			}
			if (calculateHorizontalScrollOffset >= currentHorizontalScrollOffset)
				return i;
		}

		return position;
	}

	@Override
	public void addScroll()
	{
		final int typeRotation = this.TYPE_ROTATION;
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				//Log.i("scrool",String.valueOf(newState));
//				recyclerView.getLayoutManager().f
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				int positionScroll = getPositionScroll();
				Log.i("position",String.valueOf(positionScroll));

				if (typeRotation == LinearLayoutManager.HORIZONTAL) {
					GoogleMap mMap = ((MapsActivity) context).getmMap();
					Marker marker = MapsActivity.listMarker.get(positionScroll);
					LatLng position = marker.getPosition();
					GoogleMapBasic.getInstance(mMap, context).animateCamera(position, 17.0f);
					marker.showInfoWindow();
				}
			}
		});
	}
}

