package com.vunam.mylibrary.Toolbar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 6/9/2018.
 */

public class Display {
  public static void displayBackButton(AppCompatActivity appCompatActivity)
  {
     appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
  }
}
