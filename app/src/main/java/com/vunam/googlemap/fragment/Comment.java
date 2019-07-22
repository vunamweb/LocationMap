package com.vunam.googlemap.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vunam.googlemap.MapsActivity;
import com.vunam.googlemap.R;
import com.vunam.googlemap.adapter.MyItemRecyclerViewAdapter;
import com.vunam.googlemap.adapter.ReviewAdapter;
import com.vunam.googlemap.model.LocationNear;
import com.vunam.googlemap.model.Review;
import com.vunam.googlemap.view.RecycleViewLocationNear;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.utils.Mapper;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Comment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Comment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private int position;

	private InterfaceFragment mListener;

	@BindView(R.id.progressBar) ProgressBar progressBar;
	@BindView(R.id.progressBar2) ProgressBar progressBar2;
	@BindView(R.id.progressBar3) ProgressBar progressBar3;
	@BindView(R.id.progressBar4) ProgressBar progressBar4;
	@BindView(R.id.progressBar5) ProgressBar progressBar5;
	@BindView(R.id.textViewRating1) TextView textViewRating1;
	@BindView(R.id.textViewRating2) TextView textViewRating2;
	@BindView(R.id.textViewRating3) TextView textViewRating3;
	@BindView(R.id.textViewRating4) TextView textViewRating4;
	@BindView(R.id.textViewRating5) TextView textViewRating5;

	@BindView(R.id.textViewRating) TextView textViewRating;
	@BindView(R.id.ratingBar) RatingBar ratingBar;
	@BindView(R.id.textViewTotalComment) TextView textViewTotalComments;

	@BindView(R.id.listComments) RecyclerView recyclerViewListComments;


	public Comment() {
		// Required empty public constructor
	}

	public Comment(int position) {
		this.position = position;
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment Comment.
	 */
	// TODO: Rename and change types and number of parameters
	public static Comment newInstance(String param1, String param2) {
		Comment fragment = new Comment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_comment, container, false);
		ButterKnife.bind(this, view);

		try {

			String rating1 = MapsActivity.listLocationNear.getJSONObject(position).getJSONArray("reviews").getJSONObject(0).optString("rating");
			String rating2 = MapsActivity.listLocationNear.getJSONObject(position).getJSONArray("reviews").getJSONObject(1).optString("rating");
			String rating3 = MapsActivity.listLocationNear.getJSONObject(position).getJSONArray("reviews").getJSONObject(2).optString("rating");
			String rating4 = MapsActivity.listLocationNear.getJSONObject(position).getJSONArray("reviews").getJSONObject(3).optString("rating");
			String rating5 = MapsActivity.listLocationNear.getJSONObject(position).getJSONArray("reviews").getJSONObject(4).optString("rating");

			textViewRating1.setText(rating1);
			textViewRating2.setText(rating2);
			textViewRating3.setText(rating3);
			textViewRating4.setText(rating4);
			textViewRating5.setText(rating5);

			progressBar.setProgress(Integer.parseInt(rating1));
			progressBar2.setProgress(Integer.parseInt(rating2));
			progressBar3.setProgress(Integer.parseInt(rating3));
			progressBar4.setProgress(Integer.parseInt(rating4));
			progressBar5.setProgress(Integer.parseInt(rating5));

			String averageRating = MapsActivity.listLocationNear.getJSONObject(position).optString("rating");
			textViewRating.setText(averageRating);
			ratingBar.setRating(Float.parseFloat(averageRating));
			textViewTotalComments.setText("(" + MapsActivity.listLocationNear.getJSONObject(position).optString("user_ratings_total") + ")");

			JSONArray array = MapsActivity.listLocationNear.getJSONObject(position).getJSONArray("reviews");
			List<Review> items = new Mapper<Review>().convertJsonArrayToList(array,Review.class);

			RecyclerViewAdapterBasic myAdapter = new ReviewAdapter(items, view.getContext())
					.setLayoutItem(R.layout.review)
					.setLayoutFooter(R.layout.review)
					.setLayoutHeader(R.layout.review);

			new RecycleViewLocationNear(view.getContext())
					.setTypeRotation(LinearLayoutManager.VERTICAL)
					.setAdapter(myAdapter)
					.setTypeLayoutItemDecoration(R.drawable.line_bottom_recycleview)
					.into(recyclerViewListComments)
					.setLayoutList()
					.init();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
//	public void onButtonPressed(Uri uri) {
//		if (mListener != null) {
//			mListener.onFragmentInteraction(uri);
//		}
//	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof InterfaceFragment) {
			mListener = (InterfaceFragment) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
}
