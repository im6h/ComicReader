package com.vu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vu.Common.Common;
import com.vu.Interface.IRecycleViewItemClickListener;
import com.vu.Models.Chapter;
import com.vu.ViewComicActivity;
import com.vu.comicreader.R;

import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.ViewHolder> {
    private Context context;
    private List<Chapter> mChapter;

    public MyChapterAdapter(Context context, List<Chapter> mChapter) {
        this.context = context;
        this.mChapter = mChapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_chapter_numb.setText(mChapter.get(i).getName());
        viewHolder.setiRecycleViewItemClickListener(new IRecycleViewItemClickListener() {
            @Override
            public void onClick(View view, int postion) {
                Common.chapterSelected = mChapter.get(postion);
                Common.chapterIndex = postion;
                context.startActivity(new Intent(context, ViewComicActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_chapter_numb;
        IRecycleViewItemClickListener iRecycleViewItemClickListener;

        public void setiRecycleViewItemClickListener(IRecycleViewItemClickListener iRecycleViewItemClickListener) {
            this.iRecycleViewItemClickListener = iRecycleViewItemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_chapter_numb = (TextView) itemView.findViewById(R.id.tv_chapter_numb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iRecycleViewItemClickListener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
}
