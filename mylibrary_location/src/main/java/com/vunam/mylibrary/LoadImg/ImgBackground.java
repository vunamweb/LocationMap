package com.vunam.mylibrary.LoadImg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.vunam.mylibrary.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nvu7 on 6/8/2018.
 */

public class ImgBackground {
    protected String UrlImg;
    public ImgBackground(String url)
    {
        this.UrlImg=url;
    }
    public void into(final ImageView img)
    {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected  void onPreExecute()
            {
                img.setImageResource(R.drawable.loading);
            }
            @Override
            protected Bitmap doInBackground (Void... params)
            {
                URL url = null;
                try {
                    url = new URL(UrlImg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    Bitmap bmp = null;
                    HttpURLConnection conn= (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    int length = conn.getContentLength();
                    int[] bitmapData =new int[length];
                    byte[] bitmapData2 =new byte[length];
                    InputStream is = conn.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    bmp = BitmapFactory.decodeStream(is,null,options);
                    return bmp;
                    //img.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }


            }
            @Override
            protected void onProgressUpdate(Void... progress) {


            }
            @Override
            public void onPostExecute(Bitmap result)
            {
                if(result==null)
                    img.setImageResource(R.drawable.error);
                else
                    img.setImageBitmap(result);
            }
        }.execute();
    }
}
