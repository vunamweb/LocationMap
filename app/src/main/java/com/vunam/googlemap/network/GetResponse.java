package com.vunam.googlemap.network;

import android.content.Context;

import com.vunam.mylibrary.network.NetworkUtils;

public class GetResponse extends NetworkUtils {

	public GetResponse(Context context, String url) {
		super(context,url);
	}

	@Override
	public Object registerNotification()
	{
		return null;
	}

	@Override
	public void updateGUI(Object result)
	{

	}
}
