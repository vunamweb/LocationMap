package com.vunam.mylibrary.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 7/4/2018.
 */

abstract public class RecyclerViewAdapterBasic<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> data;
    protected List<T> copyData;
    protected Context context;
    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_FOOTER = 2;
    protected int layoutItem;
    protected int layoutFooter;
    protected int layoutHeader;

    public RecyclerViewAdapterBasic(List<T> data, Context context) {
        this.data = data;
        this.copyData=data;
        this.context = context;
    }

    public RecyclerViewAdapterBasic setLayoutItem(int layoutItem)
    {
        this.layoutItem=layoutItem;
        return this;
    }

    public RecyclerViewAdapterBasic setLayoutFooter(int layoutFooter)
    {
        this.layoutFooter=layoutFooter;
        return this;
    }

    public RecyclerViewAdapterBasic setLayoutHeader(int layoutHeader)
    {
        this.layoutHeader=layoutHeader;
        return this;
    }

    public List<T> getData()
    {
        return copyData;
    }
    public void setData(List<T> data)
    {
        this.copyData=data;
    }

    public void removeItem(int position) {
        copyData.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Object item, int position) {
        //copyData.add(position,item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
         bindHolder(holder,position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_ITEM:
                return getViewItem(inflater.inflate(layoutItem, parent, false));
            case TYPE_FOOTER:
                return getViewFooter(inflater.inflate(layoutFooter, parent, false));
            case TYPE_HEADER:
                return getViewHeader(inflater.inflate(layoutHeader, parent, false));
            default:
                return getViewItem(inflater.inflate(layoutItem, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return (copyData != null)? copyData.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;

        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > data.size()-2;
    }

    abstract public RecyclerView.ViewHolder getViewItem(View view);

    abstract public RecyclerView.ViewHolder getViewFooter(View view);

    abstract public RecyclerView.ViewHolder getViewHeader(View view);

    abstract public void bindHolder(RecyclerView.ViewHolder holder, final int position);


}
