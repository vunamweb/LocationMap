package com.vunam.mylibrary.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.vunam.mylibrary.common.Constants;
import com.vunam.mylibrary.utils.Android;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nvu7 on 5/23/2018.
 */

abstract public class NetworkUtils {

	private static NetworkUtils mInstance;
	Context context;
	String url;
	String idGCM;

	public NetworkUtils(Context context, String url) {
		this.context = context;
		this.url = url;
	}

	public String getIdGCM() {
		return idGCM;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getUrl() {
		return url;
	}

	public void setIdGCM(String idGCM) {
		this.idGCM = idGCM;
	}

	//    public static synchronized NetworkUtils getInstance(Context context)
//    {
//        if(mInstance == null){
//            mInstance = new NetworkUtils(context);
//        }
//        return mInstance;
//    }
	public XmlPullParser getRss() {
		URL url = null;
		XmlPullParser myparser = null;
		try {
			url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			// Starts the query
			conn.connect();
			InputStream stream = conn.getInputStream();

			XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
			myparser = xmlFactoryObject.newPullParser();

			myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			myparser.setInput(stream, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myparser;
	}

	public JSONObject getResponse(Map<String, Object> header) throws JSONException {
		URL url;
		String response = "";
		try {
			url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(NetworkConstants.TIME_TIMEOUT);
			conn.setConnectTimeout(NetworkConstants.TIME_TIMEOUT);
			conn.setRequestMethod(Constants.METHOD_GET);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			conn.connect();
			//checks server's status code first
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			} else {
				response = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONObject(response);
	}

	public JSONObject getResponse(Map<String, String> header,String data) throws JSONException {
		URL url;
		String response = "";
		try {
			url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//			for(Map.Entry<String, String> entry : header.entrySet()) {
//				String key = entry.getKey();
//				String value = entry.getValue();
//
//				conn.setRequestProperty(key, value);
//			}
			//conn.setReadTimeout(NetworkConstants.TIME_TIMEOUT);
			//conn.setConnectTimeout(NetworkConstants.TIME_TIMEOUT);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			//conn.setDoOutput(true);
			//conn.setDoInput(true);

			for(Map.Entry<String, String> entry : header.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				conn.setRequestProperty(key, value);
			}

			//sendData(conn,data);

			conn.connect();
			//checks server's status code first
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			} else {
				response = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONObject(response);
	}

	private void sendData(HttpURLConnection con, String data) throws IOException {
		DataOutputStream wr = null;
		try {
			wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();
		} catch(IOException exception) {
			throw exception;
		} finally {
			//this.closeQuietly(wr);
		}
	}

	public JSONArray getResponseArray(Map<String, Object> header) throws JSONException {
		URL url;
		String response = "";
		try {
			url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(NetworkConstants.TIME_TIMEOUT);
			conn.setConnectTimeout(NetworkConstants.TIME_TIMEOUT);
			conn.setRequestMethod(Constants.METHOD_GET);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			conn.connect();
			//checks server's status code first
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			} else {
				response = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONArray(response);
	}

	public String uploadFile(String sourceFileUri) {
		String fileName = sourceFileUri;
		String response = "";

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			Log.e("uploadFile", "Source File not exist :");
		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
				URL url = new URL(this.url);

				// Open a HTTP  connection to  the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of  maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				int serverResponseCode = conn.getResponseCode();
				if (serverResponseCode == HttpsURLConnection.HTTP_OK) {
					String line;
					BufferedReader br = new BufferedReader(new InputStreamReader(
							conn.getInputStream()));
					while ((line = br.readLine()) != null) {
						response += line;
					}
				} else {
					response = "";
				}
				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {
				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {
				Log.e("Upload file to server", "Exception : " + e.getMessage(), e);
			}
		} // End else block
		return response;
	}

	public String getResponseHtml(String url) throws IOException {
		HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
		HttpGet httpget = new HttpGet(url); // Set the action you want to do
		HttpResponse response = httpclient.execute(httpget); // Executeit
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent(); // Create an InputStream with the response
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String line = null;
		if (reader != null) {
			while ((line = reader.readLine()) != null) // Read line by line
				sb.append(line + "\n");
		}

		String resString = sb.toString(); // Result is here
		return resString;
	}

	public void regIdCloudMessage(final String PROJECT_NUMBER) {
		new AsyncTask<Object, Void, Object>() {
			@Override
			protected Object doInBackground(Object... params) {
				String msg, regid;
				Object result = null;
				GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
				try {
					SharedPreferences prefs = context.getSharedPreferences("GCM", MODE_PRIVATE);
					regid = prefs.getString("Notification", "");
					regid = new Android.MySharedPreferences(context).getSharedPreferences("Notification");
					if (regid.equals("")) {
						regid = gcm.register(PROJECT_NUMBER);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("Notification", regid);
						editor.commit();
						new Android.MySharedPreferences(context).putSharedPreferences("Notification", regid);
					}
					url = url + "?regID=" + regid;
					msg = "Device registered, registration ID=" + regid;
					Log.i("GCM", msg);
					result = registerNotification();
				} catch (IOException ex) {
					Log.i("Error :", ex.getMessage());
				}
				return result;
			}

			@Override
			protected void onPostExecute(Object result) {
				updateGUI(result);
			}
		}.execute();
	}

	public abstract Object registerNotification();

	public abstract void updateGUI(Object result);
}


