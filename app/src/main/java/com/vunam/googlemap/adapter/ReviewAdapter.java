package com.vunam.googlemap.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vunam.googlemap.R;
import com.vunam.googlemap.fragment.ReviewComment;
import com.vunam.googlemap.model.Photo;
import com.vunam.googlemap.model.Review;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.LoadImg.ImgBackground;
import com.vunam.mylibrary.utils.Android;

import java.util.List;

public class ReviewAdapter extends RecyclerViewAdapterBasic<Review> {

	public ReviewAdapter(List<Review> data, Context context)
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
		holderItem.textViewAuthorName.setText(getData().get(position).getAuthor_name());
		holderItem.ratingBarReview.setRating(Float.parseFloat(getData().get(position).getRating()));
		holderItem.textViewRelativeTime.setText(getData().get(position).getRelative_time_description());
		holderItem.textViewDescription.setText(getData().get(position).getText());

		try {
			new ImgBackground(getData().get(position).getProfile_photo_url()).into(holderItem.imageView);
			//new ImgPicasso(context).load(url, holderItem.imageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class RecyclerViewHolder extends RecyclerView.ViewHolder {

		private ImageView imageView;
		private TextView textViewAuthorName;
		private RatingBar ratingBarReview;
		private TextView textViewRelativeTime;
		private TextView textViewDescription;

		public RecyclerViewHolder(View view) {
			super(view);
			imageView = (ImageView) view.findViewById(R.id.imageViewProfile);
			textViewAuthorName = (TextView) view.findViewById(R.id.textViewAuthorName);
			ratingBarReview = (RatingBar) view.findViewById(R.id.ratingBarReview);
			textViewRelativeTime = (TextView) view.findViewById(R.id.textViewRelativeTime);
			textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
		}
	}
}
