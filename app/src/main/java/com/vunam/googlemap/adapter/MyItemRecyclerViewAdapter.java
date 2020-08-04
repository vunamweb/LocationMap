package com.vunam.googlemap.adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vunam.googlemap.MapsActivity;
import com.vunam.googlemap.R;
import com.vunam.googlemap.model.LocationMap;
import com.vunam.googlemap.model.LocationNear;
import com.vunam.googlemap.model.Photo;
import com.vunam.googlemap.view.RecycleViewLocationNear;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.utils.Android;

import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerViewAdapterBasic<LocationNear> {

	public MyItemRecyclerViewAdapter(List<LocationNear> data, Context context) {
		super(data, context);
	}

	@Override
	public RecyclerView.ViewHolder getViewItem(View view) {
		return new RecyclerViewHolder(view);
	}

	@Override
	public RecyclerView.ViewHolder getViewFooter(View view) {
		return new RecyclerViewHolder(view);
	}

	@Override
	public RecyclerView.ViewHolder getViewHeader(View view) {
		return new RecyclerViewHolder(view);
	}

	@Override
	public void bindHolder(RecyclerView.ViewHolder holder, final int position) {

		RecyclerViewHolder holderItem = (RecyclerViewHolder) holder;

		try {
			double lat = getData().get(position).getGeometry().getLocation().getLat();
			double lng = getData().get(position).getGeometry().getLocation().getLng();
			holderItem.textViewDistance.setText(Android.distance2Location(MapsActivity.currentLatitude, MapsActivity.currentLongitude, lat, lng));
			holderItem.textViewName.setText(getData().get(position).getName());
			holderItem.textViewNumberRating.setText(getData().get(position).getRating());
			holderItem.textViewTotalComment.setText("(" + getData().get(position).getUser_ratings_total() + ")");
			holderItem.ratingBar.setRating(Float.parseFloat(getData().get(position).getRating()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		RecyclerView galery = holderItem.galery;
		galery.setHasFixedSize(true);

		String apiKey = context.getResources().getString(R.string.google_maps_key);
		String url = context.getResources().getString(R.string.url_photoreference);

		List<Photo> listPhoto = getData().get(position).getPhotos();
		//List<Photo> listPhoto = new ArrayList<Photo>();

//		for (int i = 0; i < 10; i++) {
//			Photo photo = new Photo("ddzzzzz");
//			listPhoto.add(photo);
//		}

		RecyclerViewAdapterBasic myAdapter = new ListImageAdapter(listPhoto, context, position)
				.setLayoutItem(R.layout.item_image_1)
				.setLayoutFooter(R.layout.item_image_1)
				.setLayoutHeader(R.layout.item_image_1);

		new RecycleViewLocationNear(context)
				.setTypeRotation(LinearLayoutManager.HORIZONTAL)
				.setAdapter(myAdapter)
				.setTypeLayoutItemDecoration(R.drawable.line_bottom_recycleview)
				.into(galery)
				.setLayoutList()
				.init();

		if(this.layoutItem == R.layout.fragment_item_horizontal) {
			galery.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
				@Override
				public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

					int action = e.getAction();
					switch (action) {
						case MotionEvent.ACTION_MOVE:
							rv.getParent().requestDisallowInterceptTouchEvent(true);
							break;
					}
					return false;
				}

				@Override
				public void onTouchEvent(RecyclerView rv, MotionEvent e) {

				}

				@Override
				public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

				}

			});
		}
//		try {
//			Log.i("ddtest","dd");
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	public class RecyclerViewHolder extends RecyclerView.ViewHolder {

		TextView textViewName;
		TextView textViewDistance;
		TextView textViewNumberRating;
		TextView textViewTotalComment;
		RatingBar ratingBar;
		RecyclerView galery;

		public RecyclerViewHolder(View view) {
			super(view);
			textViewName = (TextView) view.findViewById(R.id.name);
			textViewDistance = (TextView) view.findViewById(R.id.textViewDistance);
			textViewNumberRating = (TextView) view.findViewById(R.id.textViewNumberRating);
			textViewTotalComment = (TextView) view.findViewById(R.id.textViewTotalComment);
			ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
			galery = (RecyclerView) view.findViewById(R.id.list_image);
		}
	}
}
