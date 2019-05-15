package com.vunam.mylibrary.utils;

import android.transition.ChangeTransform;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vunam.mylibrary.Notification.NotificationBasic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Mapper<T> {

	public List<T> convertJsonArrayToList(JSONArray jsonArray,Class classModel)
	{
		ArrayList result = new ArrayList();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		for(int i=0;i<jsonArray.length();i++){
			JSONObject tem = null;
			try {
				tem = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
				Log.i("findAll_ERROR", e.getMessage());
			}
			T model  = null;
			try {
				model = (T) objectMapper.readValue(tem.toString(),classModel);
			} catch (IOException e) {
				Log.i("findAll_ERROR", e.getMessage());
			}
			result.add(model);
		}
        return result;
	}
}
