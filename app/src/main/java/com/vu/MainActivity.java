package com.vu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vu.Adapter.MySliderAdapter;
import com.vu.Interface.IBannerLoadDone;
import com.vu.Service.PicassoLoadingService;
import com.vu.comicreader.R;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements IBannerLoadDone {

    private Slider slider;
    private SwipeRefreshLayout swipeRefreshLayout;
    private IBannerLoadDone iBannerLoadDone;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);
        slider = findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());
        reference = FirebaseDatabase.getInstance().getReference("Banners");
        iBannerLoadDone= this;
        swipeRefreshLayout = findViewById(R.id.swipe_to_fresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
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

    }
    private void loadBanner(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String image = snapshot.getValue(String.class);
                    bannerList.add(image);
                }
                // call listener
                iBannerLoadDone.onBannerLoadDoneListener(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadComic(){

    }

    @Override
    public void onBannerLoadDoneListener(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));
    }
}
