package com.vu;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vu.Adapter.MyViewPaperAdapter;
import com.vu.Common.Common;
import com.vu.Models.Chapter;
import com.vu.comicreader.R;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

public class ViewComicActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private View back, next;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comic);

        textView = findViewById(R.id.page_number);
        viewPager = findViewById(R.id.view_paper);

        // back button
        back = findViewById(R.id.chapter_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.chapterIndex == 0) {
                    Toast.makeText(getApplicationContext(), "First page", Toast.LENGTH_SHORT).show();
                } else {
                    Common.chapterIndex--;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                }
            }
        });
        // next button

        next = findViewById(R.id.chapter_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.chapterIndex == Common.chapterList.size() - 1) {

                    Toast.makeText(getApplicationContext(), "End page", Toast.LENGTH_SHORT).show();
                } else {

                    Common.chapterIndex++;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));

                }
            }
        });

        fetchLinks(Common.chapterSelected);

    }

    private void fetchLinks(Chapter chapter) {
        if (chapter.getLinks() != null) {
            if (chapter.getLinks().size() > 0) {

                textView.setText(chapter.getName());
                MyViewPaperAdapter viewPaperAdapter = new MyViewPaperAdapter(getBaseContext(), chapter.getLinks());
                viewPager.setAdapter(viewPaperAdapter);
                BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                viewPager.setPageTransformer(true, bookFlipPageTransformer);

            } else {

                Toast.makeText(getApplicationContext(), "No Image", Toast.LENGTH_SHORT).show();

            }
        }


    }
}
