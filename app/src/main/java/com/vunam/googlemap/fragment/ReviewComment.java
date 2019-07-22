package com.vunam.googlemap.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vunam.googlemap.MapsActivity;
import com.vunam.googlemap.R;
import com.vunam.googlemap.view.ViewPagerReviewComment;
import com.vunam.mylibrary.Adapter.ViewPagerAdapterBaisc;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReviewComment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewComment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewComment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private int position;
	private BottomSheetBehavior mBottomSheetBehavior;

	@BindView(R.id.viewpager) ViewPager viewPagerReviewComment;
	@BindView(R.id.review_comment) View mLayoutBottomSheet;

	@BindView(R.id.tabs) TabLayout tabLayout;
	@BindView(R.id.textViewName) TextView textViewName;
	@BindView(R.id.textViewNumberRating) TextView textViewNumberRating;
	@BindView(R.id.textViewTotalComment) TextView textViewTotalComment;
	@BindView(R.id.ratingBar) RatingBar ratingBar;

	private InterfaceFragment mListener;

	public ReviewComment() {
		// Required empty public constructor
	}
	public ReviewComment(int position) {
		// Required empty public constructor
		super();
		this.position = position;
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ReviewComment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ReviewComment newInstance(String param1, String param2) {
		ReviewComment fragment = new ReviewComment();
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
		View view = inflater.inflate(R.layout.fragment_review_comment, container, false);
		ButterKnife.bind(this, view);
		Context context = view.getContext();

		mBottomSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);
		mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				switch (newState) {
					case BottomSheetBehavior.STATE_EXPANDED:
						Log.i("status","expand");
						break;
					case BottomSheetBehavior.STATE_HIDDEN:
						//Log.i("status","hidezzz");
						mListener.showListHorizontal();
						break;
					default:
						Log.i("status","default");
						break;

				}
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {
				Log.i("status","onslide");
			}
		});

		try {
			String rating = MapsActivity.listLocationNear.getJSONObject(position).optString("rating");
			textViewName.setText(MapsActivity.listLocationNear.getJSONObject(position).optString("name"));
			textViewNumberRating.setText(rating);
			textViewTotalComment.setText("(" + MapsActivity.listLocationNear.getJSONObject(position).optString("user_ratings_total") + ")");
			//ratingBar.setNumStars(5);
			ratingBar.setRating(Float.parseFloat(rating));
			//ratingBar.setRating((float)3.5);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		//ViewPagerAdapter
		ViewPagerAdapterBaisc adapter = new ViewPagerAdapterBaisc(((FragmentActivity)context).getSupportFragmentManager());
		adapter.addFragment(new Review(position),"review");
		adapter.addFragment(new Comment(position),"Comment");

		//setup viewpager
		new ViewPagerReviewComment(((FragmentActivity)context).getApplicationContext())
				.setViewPagerAdapter(adapter)
				.setTabLayout(tabLayout)
				.into(viewPagerReviewComment);

		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

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
//	public interface OnFragmentInteractionListener {
//		// TODO: Update argument type and name
//		void onFragmentInteraction(Uri uri);
//	}
}
