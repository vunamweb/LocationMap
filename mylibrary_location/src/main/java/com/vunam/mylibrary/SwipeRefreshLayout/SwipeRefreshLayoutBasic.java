package com.vunam.mylibrary.SwipeRefreshLayout;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by nvu7 on 7/11/2018.
 */

abstract public class SwipeRefreshLayoutBasic {
    public SwipeRefreshLayout swipeRefreshLayout;

    public SwipeRefreshLayoutBasic into(SwipeRefreshLayout swipeRefreshLayout)
    {
      this.swipeRefreshLayout=swipeRefreshLayout;
      return this;
    }

    public SwipeRefreshLayoutBasic setColor(int color1,int color2,int color3,int color4)
    {
        //swipeRefreshLayout.setColorSchemeColors(color1,color2,color3,color4);
        return this;
    }

    public void init()
    {
        setOnRefresh();
    }
    abstract public void setOnRefresh();
}
