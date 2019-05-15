package com.vunam.mylibrary.multithreading;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 5/30/2018.
 */

abstract public class ProcessAsyncTask extends AsyncTask<Object, Void, Object> {

    public ProcessAsyncTask() {

    }

    @Override
    protected Object doInBackground(Object... arg) {
        return getBackground();
    }

    @Override
    protected void onPostExecute(Object result) {
        updateGUI(result);
    }

    abstract public Object getBackground();

    abstract public void updateGUI(Object result);
}
