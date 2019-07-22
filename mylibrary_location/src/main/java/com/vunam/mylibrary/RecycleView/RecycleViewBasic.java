package com.vunam.mylibrary.RecycleView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
/**
 * Created by nvu7 on 7/3/2018.
 */

abstract public class RecycleViewBasic {
    protected Context context;
    protected RecyclerView recyclerView;
    //protected RecyclerView.LayoutManager linearLayout;
    protected RecyclerView.Adapter adapter;
    protected  int TYPE_ROTATION;//TYPE_ROTATION IS VERTICAL OR Horizontal
    protected  int TYPE_LAYOUT_ITEMDECORATION;

    public RecycleViewBasic(Context context) {
        this.context = context;
    }

    public RecycleViewBasic setTypeRotation(int typeRotation)
    {
        this.TYPE_ROTATION=typeRotation;
        return this;
    }

    public RecycleViewBasic setTypeLayoutItemDecoration(int typeLayoutItemDecoration)
    {
        this.TYPE_LAYOUT_ITEMDECORATION=typeLayoutItemDecoration;
        return this;
    }

    public RecycleViewBasic setAdapter(RecyclerView.Adapter adapter)
    {
        this.adapter=adapter;
        return this;
    }
    public RecycleViewBasic into(RecyclerView recyclerView)
    {
        this.recyclerView=recyclerView;

        // init LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setOrientation(TYPE_ROTATION);
//        this.linearLayout=layoutManager;
//        StaggeredGridLayoutManager layoutGrid=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        this.linearLayout=layoutGrid;
        //init DividerItemDecoration
        DividerItemDecoration divider = new DividerItemDecoration(
                recyclerView.getContext(),
                TYPE_ROTATION
        );
        divider.setDrawable(ContextCompat.getDrawable(context, TYPE_LAYOUT_ITEMDECORATION));
        //init recycleview
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(layoutGrid);
        recyclerView.addItemDecoration(divider);
        return this;
    }
    public RecycleViewBasic setLayoutList()
    {
        //LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,TYPE_ROTATION,false);
        //layoutManager.setOrientation(TYPE_ROTATION);
        //this.linearLayout=layoutManager;
        recyclerView.setLayoutManager(layoutManager);
        return this;
    }
    public RecycleViewBasic setLayoutGrid()
    {
        GridLayoutManager layoutManager=new GridLayoutManager(context,2);
        //RecyclerView.LayoutManager layoutManager=(RecyclerView.LayoutManager)layoutGrid;
        recyclerView.setLayoutManager(layoutManager);
        //this.linearLayout=layoutGrid;
        return this;
    }
    public void init()
    {
        addScroll();
    }
    abstract public void  addScroll();
}
