package com.vunam.mylibrary.LoadImg;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.vunam.mylibrary.R;

/**
 * Created by nvu7 on 6/8/2018.
 */

public class ImgPicasso {
    Context context;
    public ImgPicasso(Context context)
    {
        this.context=context;
    }
    public void load(String urlImg, ImageView img)
    {
        Picasso.with(context).load(urlImg).into(img);
    }
    public void loadPlaceHoder(String urlImg, ImageView img)
    {
        Picasso.with(context)
                .load(urlImg)
                .placeholder(R.drawable.loading)
                .into(img);
    }
    public void loadPlaceHoderAndError(String urlImg, ImageView img)
    {
        Picasso.with(context)
                .load(urlImg)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(img);
    }
}
