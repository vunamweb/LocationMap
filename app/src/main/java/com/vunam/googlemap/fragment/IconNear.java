package com.vunam.googlemap.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vunam.googlemap.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IconNear.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IconNear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IconNear extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private InterfaceFragment mListener;
	private BottomSheetBehavior mBottomSheetBehavior;

	@BindView(R.id.imageViewRestaurant) ImageView mButtonShowBottomSheet;
	@BindView(R.id.icon_near) View mLayoutBottomSheet;

	public IconNear() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment IconNear.
	 */
	// TODO: Rename and change types and number of parameters
	public static IconNear newInstance(String param1, String param2) {
		IconNear fragment = new IconNear();
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
		View view = inflater.inflate(R.layout.fragment_icon_near, container, false);
		ButterKnife.bind(this, view);

		mBottomSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);
		mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				switch (newState) {
					case BottomSheetBehavior.STATE_EXPANDED:
						Log.i("status","expand");
						break;
					case BottomSheetBehavior.STATE_HIDDEN:
						Log.i("status","hide");
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

	@OnClick(R.id.imageViewRestaurant)
    public  void showRestaurant ()
	{
		mListener.showListVertical(R.drawable.restaurant_marker);
	}

	@OnClick(R.id.imageViewcoffee)
	public  void showCoffee ()
	{
		mListener.showListVertical(R.drawable.coffee_marker);
	}

	@OnClick(R.id.imageViewPark)
	public  void showPark ()
	{
		mListener.showListVertical(R.drawable.park_marker);
	}

	@OnClick(R.id.imageViewkaraoke)
	public  void showKaraoke ()
	{
		mListener.showListVertical(R.drawable.karaoke_marker);
	}

	@OnClick(R.id.imageViewAtm)
	public  void showAtm ()
	{
		mListener.showListVertical(R.drawable.atm_marker);
	}

	@OnClick(R.id.imageViewAirport)
	public  void showAirport ()
	{
		mListener.showListVertical(R.drawable.airport_marker);
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
//		void show();
//	}
}
