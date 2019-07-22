package com.vunam.mylibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;
import com.vunam.mylibrary.common.Constants;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class Android {

	public static Boolean isActivityRunning(Class activityClass, Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

		for (ActivityManager.RunningTaskInfo task : tasks) {
			if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
				return true;
		}

		return false;
	}

	public static void startActivity(Context context, Class to, Bundle bundle) {
		Intent intent = new Intent(context, to);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(Constants.INTENT_DATA, bundle);
		context.startActivity(intent);
	}


	public static void startActivityPhoneCall(Context context, String numberPhone) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + numberPhone));//change the number
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions((Activity) context, new String[]{
							Manifest.permission.CALL_PHONE},
					1);
		}
		context.startActivity(callIntent);
	}

	public static void startActivityShare(Context context, String uri, String subject, String title) {
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
		context.startActivity(Intent.createChooser(sharingIntent, title));
	}

	public static void startService(Context context, Class to, Bundle bundle) {
        Intent intent=new Intent(context, to);
        //String message=bundle.getString("message");
        intent.putExtra(Constants.INTENT_DATA,bundle);
        context.startService(intent);
    }

    public static void wakeupService(Context context, Class to , Bundle bundle)
    {
        Intent intent = new Intent(context, to);
        intent.putExtra(Constants.INTENT_DATA,bundle);
        startWakefulService(context, intent);
    }

    public static void setFlag(Activity activity)
    {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static String getValueFromKeyOfBundle(Activity activity,String nameBundle,String key )
	{
		return activity.getIntent().getBundleExtra(nameBundle).getString(key);
	}

	public static class MySharedPreferences
	{
		SharedPreferences sharedPreferences;
		static MySharedPreferences instance;

		static MySharedPreferences getInstance(Context context)
		{
			if(instance == null)
				instance = new MySharedPreferences(context);
			return instance;
		}

		public MySharedPreferences(Context context)
		{
			this.sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES, MODE_PRIVATE);;
		}

		public String getSharedPreferences(String name)
		{
			return sharedPreferences.getString(name,"");
		}

		public void putSharedPreferences(String key,String value)
		{
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}

	public static class MyProgressDialog {
		Context context;
		ProgressDialog progressDialog;
		private static MyProgressDialog instance;

		public static MyProgressDialog getInstance(Context context) {
			if(instance == null)
				instance = new MyProgressDialog(context);
			return instance;
		}

		private MyProgressDialog(Context context) {
			this.context = context;
			this.progressDialog = new ProgressDialog(context);
		}

		public MyProgressDialog setTitle(String title) {
			this.progressDialog.setTitle(title);
			return this;
		}

		public MyProgressDialog setMessage(String message) {
			this.progressDialog.setMessage(message);
			return this;
		}

		public void show() {
			this.progressDialog.show();
		}

		public void hide() {
			this.progressDialog.dismiss();
		}
	}

	public static class MyProgressBar {
		Context context;
		ProgressBar progressbar;
		private static MyProgressBar instance;

		public static MyProgressBar getInstance(Context context,ProgressBar progressBar) {
			if(instance == null)
				instance = new MyProgressBar(context,progressBar);
			return instance;
		}

		public MyProgressBar(Context context, ProgressBar progressBar) {
			this.context = context;
			this.progressbar = progressBar;
		}

		public void show() {
			this.progressbar.setVisibility(ProgressBar.VISIBLE);
		}

		public void hide() {
			this.progressbar.setVisibility(ProgressBar.INVISIBLE);
		}
	}

	public static void gotoGalery(Context context,int requestCode)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		((FragmentActivity) context).startActivityForResult(intent,requestCode);
	}

	public static void gotoGaleryChoose(Context context,int requestCode,String title)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		((FragmentActivity) context).startActivityForResult(Intent.createChooser(intent, title), requestCode);
	}

	public static void gotoRecorder(Context context,int requestCode)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		((FragmentActivity) context).startActivityForResult(intent,requestCode);
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	public static void gotoCameraCapture(Context context, int requestCode) {
		if (context.checkSelfPermission(Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED) {
			((FragmentActivity) context).requestPermissions(new String[]{Manifest.permission.CAMERA},
					Constants.REQUEST_CODE_CAMERA);
		} else {
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			((FragmentActivity) context).startActivityForResult(cameraIntent, Constants.REQUEST_CODE_CAMERA);
		}
	}

	public static void convertUriToImageView(Context context,Uri imageUri,ImageView imageView)
	{
		try {
			final InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
//			StringWriter writer = new StringWriter();
//			try {
//				IOUtils.copy(imageStream, writer,"UTF-8");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			String a= writer.toString();
			//String a = IOUtils.toString(imageStream, StandardCharsets.UTF_8);
			//InputStream stream = new ByteArrayInputStream(a.getBytes(StandardCharsets.UTF_8));
//			InputStream stream = null;
//			try {
//				stream = IOUtils.toInputStream(a,"UTF-8");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
			//encode image to base64 string
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//			byte[] imageBytes = baos.toByteArray();
			String imageString = StringUtils.encodeBitmapToStringBase64(bitmap);
			//Base64.encodeToString(imageBytes, Base64.DEFAULT);
			//decode base64 string to image
			//imageBytes = Base64.decode(imageString, Base64.DEFAULT);
			Bitmap decodedImage = StringUtils.decodeStringToBitmapBase64(imageString); //BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
			imageView.setImageBitmap(decodedImage);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static String getPathFromURI(Uri contentUri,Context context) {
		String res = null;
		String[] proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		if (cursor.moveToFirst()) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}

	public static String getFileName(Uri uri, Context context) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
				}
			} finally {
				cursor.close();
			}
		}
		if (result == null) {
			result = uri.getPath();
			int cut = result.lastIndexOf('/');
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
	}

	public static void transactionFragment(FragmentActivity context, int layout, Fragment fragment)
	{
		// Begin the transaction
		FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
		// Replace the contents of the container with the new fragment
		ft.replace(layout, fragment,"detail");
		ft.addToBackStack("detail");
		ft.commit();
	}

	public static void showDialog(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("ddd");
		AlertDialog fMapTypeDialog = builder.create();
		fMapTypeDialog.setCanceledOnTouchOutside(true);
		fMapTypeDialog.show();
	}

	public static String distance2Location(double startLatitude, double startLongitude, double endLatitude, double endLongitude)
	{
		float[] result = new float[1];
		Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, result);
		float mille = result[0]/1609.34f;
		return String.format("%.2f",mille) + " mile";
	}

	public static class DirectionsJSONParser {

		/** Receives a JSONObject and returns a list of lists containing latitude and longitude */
		public static List<List<HashMap<String,String>>> parse(JSONObject jObject){

			List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
			JSONArray jRoutes = null;
			JSONArray jLegs = null;
			JSONArray jSteps = null;

			try {

				jRoutes = jObject.getJSONArray("routes");

				/** Traversing all routes */
				for(int i=0;i<jRoutes.length();i++){
					jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
					List path = new ArrayList<HashMap<String, String>>();

					/** Traversing all legs */
					for(int j=0;j<jLegs.length();j++){
						jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

						/** Traversing all steps */
						for(int k=0;k<jSteps.length();k++){
							String polyline = "";
							polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
							List list = decodePoly(polyline);

							/** Traversing all points */
							for(int l=0;l <list.size();l++){
								HashMap<String, String> hm = new HashMap<String, String>();
								hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
								hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
								path.add(hm);
							}
						}
						routes.add(path);
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}catch (Exception e){
			}

			return routes;
		}

		/**
		 * Method to decode polyline points
		 * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
		 * */
		private static List decodePoly(String encoded) {

			List poly = new ArrayList();
			int index = 0, len = encoded.length();
			int lat = 0, lng = 0;

			while (index < len) {
				int b, shift = 0, result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lat += dlat;

				shift = 0;
				result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lng += dlng;

				LatLng p = new LatLng((((double) lat / 1E5)),
						(((double) lng / 1E5)));
				poly.add(p);
			}

			return poly;
		}
	}
}
