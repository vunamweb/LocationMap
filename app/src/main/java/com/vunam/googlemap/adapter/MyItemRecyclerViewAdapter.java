package com.vunam.googlemap.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vunam.googlemap.R;
import com.vunam.googlemap.fragment.ItemFragment.OnListFragmentInteractionListener;
import com.vunam.googlemap.model.LocationNear;
import com.vunam.googlemap.model.Photo;
import com.vunam.googlemap.recycle.RecycleViewLocationNear;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.LoadImg.ImgPicasso;
import com.vunam.mylibrary.RecycleView.RecycleViewBasic;
import com.vunam.mylibrary.utils.Android;

import java.util.ArrayList;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerViewAdapterBasic<LocationNear> {

	public MyItemRecyclerViewAdapter(List<LocationNear> data, Context context)
	{
		super(data,context);
	}

	@Override
	public RecyclerView.ViewHolder getViewItem(View view)
	{
		return new RecyclerViewHolder(view);
	}

	@Override
	public RecyclerView.ViewHolder getViewFooter(View view)
	{
		return new RecyclerViewHolder(view);
	}

	@Override
	public RecyclerView.ViewHolder getViewHeader(View view)
	{
		return new RecyclerViewHolder(view);
	}

	@Override
	public void bindHolder(RecyclerView.ViewHolder holder, final int position) {

		RecyclerViewHolder holderItem = (RecyclerViewHolder) holder;
		holderItem.txtName.setText(getData().get(position).getName());
		RecyclerView galery = holderItem.galery;
		galery.setHasFixedSize(true);

		String apiKey = context.getResources().getString(R.string.google_maps_key);
		String url = context.getResources().getString(R.string.url_photoreference);

		List<Photo> listPhoto = getData().get(position).getList_photo();
		//List<Photo> listPhoto = new ArrayList<Photo>();

//		for (int i = 0; i < 10; i++) {
//			Photo photo = new Photo("ddzzzzz");
//			listPhoto.add(photo);
//		}

		RecyclerViewAdapterBasic myAdapter = new ListImageAdapter(listPhoto, context)
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

		galery.addOnItemTouchListener (new RecyclerView.OnItemTouchListener()
		{
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

//		try {
//			Log.i("ddtest","dd");
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	public class RecyclerViewHolder extends RecyclerView.ViewHolder {

		TextView txtName;
		RecyclerView galery;

		public RecyclerViewHolder(View view) {
			super(view);
			txtName = (TextView) view.findViewById(R.id.name);
			galery = (RecyclerView) view.findViewById(R.id.list_image);
		}
	}
}
