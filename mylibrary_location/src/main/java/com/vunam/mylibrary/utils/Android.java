package com.vunam.mylibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageView;

import com.vunam.mylibrary.common.Constants;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class Android {

     public static Boolean isActivityRunning(Class activityClass,Context context)
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
    }

    public static void startActivity(Context context, Class to, Bundle bundle)
    {
        Intent intent=new Intent(context, to);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.INTENT_DATA,bundle);
        context.startActivity(intent);
    }

    public static void startService(Context context, Class to, Bundle bundle)
    {
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
		ft.replace(layout, fragment);
		ft.commit();
	}
}
