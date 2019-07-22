package com.vunam.googlemap.adapter;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vunam.googlemap.R;
import com.vunam.googlemap.fragment.ReviewComment;
import com.vunam.googlemap.model.LocationNear;
import com.vunam.googlemap.model.Photo;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.LoadImg.ImgBackground;
import com.vunam.mylibrary.LoadImg.ImgPicasso;
import com.vunam.mylibrary.RecycleView.RecycleViewBasic;
import com.vunam.mylibrary.utils.Android;

import java.util.List;


public class ListImageAdapter extends RecyclerViewAdapterBasic<Photo> {

	private int positionParentList;

	public ListImageAdapter(List<Photo> data, Context context)
	{
		super(data,context);
	}

	public ListImageAdapter(List<Photo> data, Context context, int positionParentList) {
		super(data, context);
		this.positionParentList = positionParentList;
	}

	public int getPositionParentList() {
		return positionParentList;
	}

	public void setPositionParentList(int positionParentList) {
		this.positionParentList = positionParentList;
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
		//final int postionParentListNow = this.getPositionParentList();

		String apiKey = context.getResources().getString(R.string.google_maps_key);
		String url = context.getResources().getString(R.string.url_photoreference);
		String width = getData().get(position).getWidth();
		String height = getData().get(position).getHeight();
		url = url + "?sensor=true&maxwidth=" + width + "&maxheight=" + height + "&photoreference=" + getData().get(position).getPhoto_reference() + "&key=" + apiKey;

		try {
			new ImgBackground(url).into(holderItem.imageView);
			//new ImgPicasso(context).load(url, holderItem.imageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new ImgPicasso(context).load("http://vunamweb.com/images/rokquickcart/Untitled-68.jpg", holderItem.imageView);

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Log.i("test","abcd");

				Android.transactionFragment((FragmentActivity) context,R.id.bootom_sheet,new ReviewComment(positionParentList));
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

		private ImageView imageView;

		public RecyclerViewHolder(View view) {
			super(view);
			imageView = (ImageView) view.findViewById(R.id.imageView);
		}
	}
}

