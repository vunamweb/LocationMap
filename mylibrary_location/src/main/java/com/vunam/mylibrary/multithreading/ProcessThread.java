package com.vunam.mylibrary.multithreading;

import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nvu7 on 5/24/2018.
 */

public class ProcessThread extends Thread {
    private android.os.Handler handler;
    private String url;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ProcessThread(android.os.Handler handler,String url)
    {
        this.handler=handler;
        this.url=url;
    }

    public ProcessThread(android.os.Handler handler,String url,RecyclerView recyclerView,ProgressBar progressBar)
    {
        this.handler=handler;
        this.url=url;
        this.recyclerView=recyclerView;
        this.progressBar=progressBar;
    }

    public ProcessThread(android.os.Handler handler,String url,RecyclerView recyclerView,ProgressBar progressBar,SwipeRefreshLayout swipeRefreshLayout)
    {
        this.handler=handler;
        this.url=url;
        this.recyclerView=recyclerView;
        this.progressBar=progressBar;
        this.swipeRefreshLayout=swipeRefreshLayout;
    }

    public ProcessThread(android.os.Handler handler,String url,RecyclerView.Adapter myAdapter)
    {
        this.handler=handler;
        this.url=url;
        this.myAdapter=myAdapter;
    }

    public ProcessThread(android.os.Handler handler,String url,RecyclerView.Adapter myAdapter,ProgressBar progressBar)
    {
        this.handler=handler;
        this.url=url;
        this.myAdapter=myAdapter;
        this.progressBar=progressBar;
    }

    public ProcessThread(android.os.Handler handler,String url,RecyclerView.Adapter myAdapter,SwipeRefreshLayout swipeRefreshLayout)
    {
        this.handler=handler;
        this.url=url;
        this.myAdapter=myAdapter;
        this.swipeRefreshLayout=swipeRefreshLayout;
    }

    @Override
    public void run() {

    }
}
