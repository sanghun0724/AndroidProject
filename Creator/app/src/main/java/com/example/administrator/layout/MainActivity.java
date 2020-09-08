package com.example.administrator.layout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {
    ImageView imageView1;
    ImageView imageView2;
    TextView textView1;
    TextView textView2;
    ImageButton imageButton;
    AdView madView;

    Context mcontext;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private CoordinatorLayout coordinatorLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcontext = getApplicationContext();
        Intent intent = new Intent(this, First_activity.class);
        startActivity(intent);
        adViewset();
        setrecyclerview();

    }



    public void setrecyclerview() {
        recyclerView = (RecyclerView) findViewById(R.id.recylcer_view);
        recyclerView.setHasFixedSize(true);
        ArrayList items =new ArrayList<>();
        items.add(new Item(R.drawable.indian_big,R.drawable.indian_small,"Sundar Pichai","Google CEO"));
        items.add(new Item(R.drawable.jobs_1,R.drawable.jobs_2,"Steve Jobs","Apple CEO"));
        items.add(new Item(R.drawable.musk_1,R.drawable.musk_2,"Elon Musk","Tesla CEO"));
        items.add(new Item(R.drawable.big,R.drawable.billsmall,"bill Gates","MS CEO"));
        items.add(new Item(R.drawable.markbig,R.drawable.smallnike,"Mark Parker","Nike CEO"));
        items.add(new Item(R.drawable.cook,R.drawable.smallcook,"Tim Cook","Apple CEO"));
        items.add(new Item(R.drawable.sdsd,R.drawable.smaller,"Waren Buffit","rich man"));

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Myadapter myadapter=new Myadapter(mcontext,items);
        recyclerView.setAdapter(myadapter);
        recyclerView=findViewById(R.id.recylcer_view);
    }
   public void adViewset(){
     MobileAds.initialize(this, new OnInitializationCompleteListener() {
           @Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {
          }
       });
     madView=findViewById(R.id.adView);
     AdRequest adRequest =new AdRequest.Builder().build();
      madView.loadAd(adRequest);
    }
}