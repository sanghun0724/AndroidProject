package com.example.administrator.layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


//import com.example.administrator.myapplication.PreferenceManager;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.Define_value;
import com.example.administrator.myapplication.PreferenceManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String HI = "https://engvocaapp.000webhostapp.com/wordfile.php";
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    ImageView imageView;
    RecyclerView recyclerView;
    private ArrayList<Product_list> product_list;
    LinearLayoutManager linearLayoutManager;
    Context mcontext;
    TextView tvLevel,tv_levelTitle;
    ProductAdapter adapter;
    Context mContext;
    private CoordinatorLayout coordinatorLayout;
    //데이터
   private JsonArrayRequest request;
   private RequestQueue requestQueue;

    private static int LAST_PAGE;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      innitset();
    //  LAST_PAGE= PreferenceManager.getinSharedpreference(mcontext, Database.PREFERENCE_LAST_PAGE_VALUE,0);
        setInrecylerview();
       // imageView = (this).findViewById(R.id.nav_haederview);
        //Glide.with(this).load(R.drawable.goodman).into(imageView);

        getData();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();
        String title = menuItem.getTitle().toString();
        if (id == R.id.nav_purchase) {
            Toast.makeText(this, title + "계정 정보를 확인하세요", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_refund) {
            Toast.makeText(this, title + "건영이는환불이안돼용", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_reivew) {
            Toast.makeText(this, title + "아라라라아아랑", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_question) {
            Toast.makeText(this, title + "모요모요모요", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;


    }
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }//oncreate > onstart 사이 메소드 임
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
//        안드로이드의 경우 화면이 회전되었을 경우 화면을 종료시키고 새로운 layout으로 재 시작한다.
//
//        즉 세로화면 -> 가로화면으로 전환 시 onDestroy() 함수가 호출되고 가로모드에서 다시 onCreate() 함수가 호출된다.
//
//        즉 환경변화가 일어날 경우 기본적인 동작은 activity 의 재시작이다.
//
//                이럴때 AndroidManifest.xml 파일에 ` android:configChanges`을 설정함으로써 activity가 reset되는것을 막을 수 있다.
//
//                이런경우 activity의 onDestroy()와 onCreate() 함수 대신에 onConfigurationChanged()함수가 호출된다.
    }








    private void innitset() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        mContext = this;
        product_list = new ArrayList<>();
        coordinatorLayout=findViewById(R.id.main_Layout);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView) findViewById(R.id.nav_view);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );


        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
  public void setInrecylerview(){
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
       // linearLayoutManager.scrollToPositionWithOffset(LAST_PAGE,0);
         //기본설정
      recyclerView.setLayoutManager(linearLayoutManager);

      final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
          pagerSnapHelper.attachToRecyclerView(recyclerView);

         SnapPagerScrollListener listener=new SnapPagerScrollListener(
                 pagerSnapHelper,
                 SnapPagerScrollListener.ON_SCROLL,
                 true,
                 new SnapPagerScrollListener.OnChangeListener() {
                     @Override
                     public void onSnapped(int position) {
                         PreferenceManager.setinSharedPreference(mContext, Define_value.PREFERENCE_LAST_PAGE_VALUE,position);
                     }
                 }
         );
         recyclerView.addOnScrollListener(listener);
      }
    private void getData() {
        request=new JsonArrayRequest(HI, new Response.Listener <JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for (int i=0; i<response.length(); i++){
                    try {;
                        JSONObject ob=response.getJSONObject(i);
                        Product_list pr=new Product_list(ob.getInt("id"),
                                ob.getString("engWord"),
                                ob.getString("engPro"),
                                ob.getString("korMean"),
                                ob.getString("korMean2"),
                                ob.getString("exKor"),
                                ob.getString("exEng"),
                                ob.getString("exEngHidden"));
                        product_list.add(pr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setupData(product_list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
        Log.i("TEST","서버에서 데이터 가져옴"); //서버 연동 발생

    }
    private void setupData(ArrayList<Product_list> product_lists) {
        adapter=new ProductAdapter(product_lists, getApplicationContext(),coordinatorLayout);
        recyclerView.setAdapter(adapter);
        Log.i("TEST","리싸이클러뷰에 데이터 붙음");
    }
  }




