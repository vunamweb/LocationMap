package com.vunam.googlemap.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vunam.googlemap.MapsActivity;
import com.vunam.googlemap.R;
import com.vunam.googlemap.adapter.MyItemRecyclerViewAdapter;
import com.vunam.googlemap.model.LocationNear;
import com.vunam.googlemap.view.RecycleViewLocationNear;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.utils.Mapper;


import org.json.JSONArray;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ListItemVertical extends Fragment {

	// TODO: Customize parameter argument names
	private static final String ARG_COLUMN_COUNT = "column-count";
	// TODO: Customize parameters
	private int mColumnCount = 1;
	private InterfaceFragment mListener;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ListItemVertical() {
	}

	// TODO: Customize parameter initialization
	@SuppressWarnings("unused")
	public static ListItemVertical newInstance(int columnCount) {
		ListItemVertical fragment = new ListItemVertical();
		Bundle args = new Bundle();
		args.putInt(ARG_COLUMN_COUNT, columnCount);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_list, container, false);

		// Set the adapter
		Context context = view.getContext();
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
		JSONArray array = ((MapsActivity) mListener).listLocationNear;
		List<LocationNear> items = new Mapper<LocationNear>().convertJsonArrayToList(array, LocationNear.class);

		RecyclerViewAdapterBasic myAdapter = new MyItemRecyclerViewAdapter(items, context)
				.setLayoutItem(R.layout.fragment_item)
				.setLayoutFooter(R.layout.fragment_item)
				.setLayoutHeader(R.layout.fragment_item);

		new RecycleViewLocationNear(context)
				.setTypeRotation(LinearLayoutManager.VERTICAL)
				.setAdapter(myAdapter)
				.setTypeLayoutItemDecoration(R.drawable.line_bottom_recycleview)
				.into(recyclerView)
				.setLayoutList()
				.init();
//			if (mColumnCount <= 1) {
//				recyclerView.setLayoutManager(new LinearLayoutManager(context));
//			} else {
//				recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//			}
//			recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));

		return view;
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof InterfaceFragment) {
			mListener = (InterfaceFragment) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnListFragmentInteractionListener");
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
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
//	public interface OnListFragmentInteractionListener {
//		// TODO: Update argument type and name
//		//void onListFragmentInteraction(DummyItem item);
//	}
}
