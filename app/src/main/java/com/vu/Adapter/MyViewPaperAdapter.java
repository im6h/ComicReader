package com.vu.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.vu.comicreader.R;

import java.util.List;

public class MyViewPaperAdapter extends PagerAdapter {
    private Context context;
    private List<String>  mList;

    public MyViewPaperAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view.equals(o);
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_paper_item,container,false);
        PhotoView view_image =view.findViewById(R.id.paper_image);
        Picasso.get().load(mList.get(position)).into(view_image);
        container.addView(view);
        return view;
    }


}
