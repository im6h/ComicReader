package com.vu.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vu.Models.Comic;
import com.vu.comicreader.R;

import java.util.List;

public class MyComicAdapter extends RecyclerView.Adapter<MyComicAdapter.ViewHolder> {

    private Context context;
    private List<Comic> comicList;

    public MyComicAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comic_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Picasso.get().load(comicList.get(i).getImage()).into(viewHolder.comicView);
        viewHolder.comicName.setText(comicList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView comicView;
        TextView comicName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comicView = itemView.findViewById(R.id.comic_image);
            comicName = itemView.findViewById(R.id.comic_name);
        }
    }
}
