package com.vunam.mylibrary.ViewPager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

//import com.example.nvu7.readrss.adapter.ViewPagerAdapter;
import com.vunam.mylibrary.Adapter.ViewPagerAdapterBaisc;

/**
 * Created by Administrator on 7/6/2018.
 */

abstract public class ViewPagerBasic {

  protected TabLayout tabLayout;
  protected ViewPager viewPager;
  protected ViewPagerAdapterBaisc viewPagerAdapter;
  protected Context context;

  public ViewPagerBasic(Context context)
  {
    this.context=context;
  }

  public ViewPagerAdapterBaisc getViewPagerAdapter() {
    return viewPagerAdapter;
  }

  public ViewPagerBasic setTabLayout(TabLayout tabLayout)
  {
    this.tabLayout=tabLayout;
    return this;
  }

  public ViewPagerBasic setViewPagerAdapter(ViewPagerAdapterBaisc viewPagerAdapter)
  {
    this.viewPagerAdapter=viewPagerAdapter;
    return this;
  }

  public ViewPagerBasic into(ViewPager viewPager)
  {
    this.viewPager=viewPager;
    viewPager.setAdapter(viewPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
    setOnPageChangeListener();
    return this;
  }
  abstract public void setOnPageChangeListener();
}
