package com.vu.Service;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;

public class PicassoLoadingService implements ImageLoadingService {
    @Override
    public void loadImage(String url, ImageView imageView) {

    }

    @Override
    public void loadImage(int resource, ImageView imageView) {

    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
    }
}
