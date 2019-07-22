package com.vunam.googlemap.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vunam.googlemap.MapsActivity;
import com.vunam.googlemap.R;
import com.vunam.mylibrary.utils.Android;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Review.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Review#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Review extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private int position;

	private InterfaceFragment mListener;

	@BindView(R.id.text_location) TextView textLocation;
	@BindView(R.id.text_phone) TextView textPhone;
	@BindView(R.id.imageViewDirection) ImageView imgViewDirection;
	@BindView(R.id.imageViewPhoneCall) ImageView imgViewPhoneCall;

	public Review() {
		// Required empty public constructor
	}

	public Review(int position) {
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
	 * @return A new instance of fragment Review.
	 */
	// TODO: Rename and change types and number of parameters
	public static Review newInstance(String param1, String param2) {
		Review fragment = new Review();
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
		View view = inflater.inflate(R.layout.fragment_review, container, false);
		ButterKnife.bind(this, view);

		try {
			textLocation.setText(MapsActivity.listLocationNear.getJSONObject(position).optString("formatted_address"));
			textPhone.setText(MapsActivity.listLocationNear.getJSONObject(position).optString("formatted_phone_number"));
		} catch (JSONException e) {
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

	@OnClick(R.id.imageViewDirection)
	public void direction()
	{
		Log.i("direction", "show");
		try {
			String destinationLng = MapsActivity.listLocationNear.getJSONObject(position).getJSONObject("geometry").getJSONObject("location").getString("lng");
			String destinationLat = MapsActivity.listLocationNear.getJSONObject(position).getJSONObject("geometry").getJSONObject("location").getString("lat");
			String currentLng = Double.toString(MapsActivity.currentLongitude);
			String currentLat = Double.toString(MapsActivity.currentLatitude);
			mListener.showDirection(currentLat,currentLng,destinationLat,destinationLng,"driving");
//			GoogleMap map = ((MapsActivity)mListener).getmMap();
//			map.addPolyline(new PolylineOptions()
//					.add(new LatLng(currentLat, currentLng), new LatLng(destinationLat, destinationLng))
//					.width(5)
//					.color(Color.RED));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@OnClick(R.id.imageViewPhoneCall)
	public void phoneCall(){
		mListener.phoneCall(textPhone.toString());
		//Android.startActivityPhoneCall((Context)mListener,"+8423466566");
	}

	@OnClick(R.id.imageViewShare)
	public void share() {
		String uri = "http://maps.google.com/maps?saddr=" +MapsActivity.currentLatitude+","+MapsActivity.currentLongitude;
		Android.startActivityShare((MapsActivity)mListener,uri,"subject","share");
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
