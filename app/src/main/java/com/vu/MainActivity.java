package com.vu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vu.Adapter.MyComicAdapter;
import com.vu.Adapter.MySliderAdapter;
import com.vu.Common.Common;
import com.vu.Interface.IBannerLoadDone;
import com.vu.Interface.IComicLoadDone;
import com.vu.Models.Comic;
import com.vu.Service.PicassoLoadingService;
import com.vu.comicreader.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements IBannerLoadDone, IComicLoadDone {

    private Slider slider;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView txtComic;
    private RecyclerView recyclerComic;
    private ImageView btnFilter;
    private android.app.AlertDialog alertDialog;


    private IBannerLoadDone iBannerLoadDone;
    private IComicLoadDone iComicLoadDone;

    private DatabaseReference banners, comics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);

        // slide
        slider = findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());

        // database
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        comics = FirebaseDatabase.getInstance().getReference("Comic");

        iBannerLoadDone = this;
        iComicLoadDone = this;

        txtComic = findViewById(R.id.txt_comic);

        // recycler view
        recyclerComic = findViewById(R.id.recycler_comic);
        recyclerComic.setHasFixedSize(true);
        recyclerComic.setLayoutManager(new GridLayoutManager(this, 2));

        // swipe
        swipeRefreshLayout = findViewById(R.id.swipe_to_fresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadComic();
            }
        });
        btnFilter = findViewById(R.id.btn_show_filter_search);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadBanner() {
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String image = snapshot.getValue(String.class);
                    bannerList.add(image);
                }
                // call listener
                iBannerLoadDone.onBannerLoadDoneListener(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadComic() {
        // Show dialog
        alertDialog = new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setMessage("Please wait.....")
                .build();
        if (!swipeRefreshLayout.isRefreshing()) {
            alertDialog.show();
        }

        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comic> comicList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comic comic = snapshot.getValue(Comic.class);
                    comicList.add(comic);
                }
                // call listener
                iComicLoadDone.onComicLoadDoneListener(comicList);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBannerLoadDoneListener(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));
    }

    @Override
    public void onComicLoadDoneListener(List<Comic> comics) {
        Common.comicList = comics;
        recyclerComic.setAdapter(new MyComicAdapter(getBaseContext(), comics));

        txtComic.setText(new StringBuilder("New Comic (").append(comics.size()).append(" )"));
        if (!swipeRefreshLayout.isRefreshing()) {
            alertDialog.dismiss();
        }
    }
}
