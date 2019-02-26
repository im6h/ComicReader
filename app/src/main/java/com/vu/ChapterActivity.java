package com.vu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vu.Adapter.MyChapterAdapter;
import com.vu.Common.Common;
import com.vu.Models.Chapter;
import com.vu.Models.Comic;
import com.vu.comicreader.R;

public class ChapterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView text_name_chapter;
    private RecyclerView rcv_chapter;
    private MyChapterAdapter chapterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        // toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.comicSelected.getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // textview
        text_name_chapter = findViewById(R.id.text_chapter);
        // recycle view
        rcv_chapter = findViewById(R.id.rcv_chapter);
        rcv_chapter.setHasFixedSize(true);
        rcv_chapter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // fetch data
        fetchData(Common.comicSelected);
    }

    private void fetchData(Comic comicSelected) {
        Common.chapterList = comicSelected.getChapters();
        chapterAdapter = new MyChapterAdapter(this, Common.chapterList);
        if (chapterAdapter != null) {
            rcv_chapter.setAdapter(chapterAdapter);
        } else {
            Toast.makeText(ChapterActivity.this, "Updating.....", Toast.LENGTH_SHORT).show();
        }
        text_name_chapter.setText(new StringBuilder("CHAP (").append(comicSelected.getChapters().size()).append(" )"));


    }
}
