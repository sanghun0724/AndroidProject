package com.ogrg.techtown.vocavocca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.ogrg.techtown.vocavocca.common.DefineValue;
import com.ogrg.techtown.vocavocca.common.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BillingProcessor.IBillingHandler {
    private static final String HI = "https://engvocaapp.000webhostapp.com/wordfile.php";
    private static int LAST_PAGE;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ArrayList<Product_List> product_lists;
    private RecyclerView recyclerView;
    private TextView tvLevel,tv_levelTitle;
    private Context mContext;
    private ProductAdapter adapter;
    private CoordinatorLayout mLinearLayout;
    private LinearLayoutManager linearLayoutManager;
    private AdView mAdView;

    // 데이터
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    public static FavoriteDatabase favoriteDatabase;

    //In-App purchase
    private BillingProcessor bp;
    private AppStorage storage;

    //Visit Counter
    SharedPreferences pref;
    private int counter;
    private String VISIT = "visit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TEST","MainActivity : onCreate 실행");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(this, LoadingActivity.class);
//        startActivity(intent);
        // toolbar fancy stuff
        setInitView();
        LAST_PAGE = PreferenceManager.getIntPreference(mContext, DefineValue.PREFERENCE_LAST_PAGE_VALUE,0);
        setInitRecyclerView();
        //Test
        checkCurrentPage(LAST_PAGE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START) ;
            }
        });

//        MobileAds.initialize(this, getString(R.string.admob_app_id));
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();
        getData();
        whiteNotificationBar(recyclerView);

        bp = new BillingProcessor(this, getResources().getString(R.string.in_app_RSA), this);
        bp.initialize();
        storage = new AppStorage(this);

        pref = getSharedPreferences("AppStorage", Context.MODE_PRIVATE);
        counter = pref.getInt(VISIT,0) + 1;
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(VISIT,counter);
        editor.commit();

        if (counter==5) {
            Log.e("VISIT", "OVER 5 ");
            //Review VocaVocca
            Intent intent = new Intent(getApplicationContext(), review_noti.class);
            startActivityForResult(intent, 1);
        }
    }

    private void showTvLevel() {
         final CharSequence[] items = { "토익 600점", "토익 700점", "토익 800점", "토익 900점" };
         AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
         alertDialogBuilder.setTitle("목표 설정하기");
         alertDialogBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 switch (whichButton){
                     case 0: //600점
                         tvLevel.setText(checkCurrentPage(whichButton));
                         linearLayoutManager.scrollToPositionWithOffset(DefineValue.ITEM_STARTPOSTION_FOR600,0);
                         dialog.dismiss();
                         break;
                     case 1: // 700점
                         tvLevel.setText(checkCurrentPage(whichButton));
                         linearLayoutManager.scrollToPositionWithOffset(DefineValue.ITEM_STARTPOSTION_FOR700,0);
                         dialog.dismiss();
                         break;
                     case 2: // 800점
                         tvLevel.setText(checkCurrentPage(whichButton));
                         linearLayoutManager.scrollToPositionWithOffset(DefineValue.ITEM_STARTPOSTION_FOR800,0);
                         dialog.dismiss();
                         break;
                     case 3: // 900점
                         tvLevel.setText(checkCurrentPage(whichButton));
                         linearLayoutManager.scrollToPositionWithOffset(DefineValue.ITEM_STARTPOSTION_FOR900,0);
                         dialog.dismiss();
                         break;
                     default:
                         break;
                 }
             }
         });
         //다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();
        //다이얼로그 보여주기
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // 검색, 보관함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // 앱 바_보관함 들어가기
        MenuItem folder = menu.findItem(R.id.action_main_folder);
        folder.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                intent.putParcelableArrayListExtra("productListFromMain",product_lists);
                startActivity(intent);

                finish();
                Log.i("TEST","MainActivity : 보관함버튼 클릭" );
                return false;
            }
        });
        MenuItem search = menu.findItem(R.id.action_main_search);
        search.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
//                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
//                overridePendingTransition(R.anim.not_move_activity,R.anim.rightin_activity);
                finish();
                return false;
            }
        });
        return true;
    }
    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void setInitView() {
        mContext = this;
        product_lists = new ArrayList<>();
        tv_levelTitle = findViewById(R.id.tv_levelTitle);
        tv_levelTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTvLevel();
            }
        });
        tvLevel = findViewById(R.id.tv_level);
        tvLevel.setText(checkCurrentPage(LAST_PAGE));
        tvLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTvLevel();
            }
        });
        mLinearLayout = findViewById(R.id.MainLayout);
    }

    private void setInitRecyclerView() {
        // 리싸이클러뷰 기본 설정
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL,false);
        linearLayoutManager.scrollToPositionWithOffset(LAST_PAGE,0);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리싸이클러뷰를 스냅처럼 출처:https://www.charlezz.com/?p=1329
        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

        // 페이지 변경 시(=스크롤 발생 시)
        SnapPagerScrollListener listener = new SnapPagerScrollListener(
                pagerSnapHelper,
                SnapPagerScrollListener.ON_SCROLL,
                true,
                // 스크롤 바뀔 때마다 위치 파악해서  난이도 변경 해주기
                new SnapPagerScrollListener.OnChangeListener() {
                    @Override
                    public void onSnapped(int position) {
                        tvLevel.setText(checkCurrentPage(position));
                        PreferenceManager.setIntPreference(mContext,DefineValue.PREFERENCE_LAST_PAGE_VALUE,position);
                        Log.i("TEST","MainActivity : SnapPagerScrollListener 실행 지금 위치는? " + position);
                    }
                });

        recyclerView.addOnScrollListener(listener);
      }

    private String checkCurrentPage(int position){
        String level = "";
        if(tvLevel != null) {
            if (DefineValue.ITEM_STARTPOSTION_FOR600 <= position && position < DefineValue.ITEM_STARTPOSTION_FOR700) {
                level = DefineValue.STATE_LEVEL_FOR600;
            } else if (DefineValue.ITEM_STARTPOSTION_FOR700 <= position && position < DefineValue.ITEM_STARTPOSTION_FOR800) {
                level = DefineValue.STATE_LEVEL_FOR700;
            } else if (DefineValue.ITEM_STARTPOSTION_FOR800 <= position && position < DefineValue.ITEM_STARTPOSTION_FOR900) {
                level = DefineValue.STATE_LEVEL_FOR800;
            }
        }
        return level;
    }

    private void getData() {
        request=new JsonArrayRequest(HI, new Response.Listener <JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for (int i=0; i<response.length(); i++){
                    try {;
                        JSONObject ob=response.getJSONObject(i);
                        Product_List pr=new Product_List(ob.getInt("id"),
                                ob.getString("engWord"),
                                ob.getString("engPro"),
                                ob.getString("korMean"),
                                ob.getString("korMean2"),
                                ob.getString("exKor"),
                                ob.getString("exEng"),
                                ob.getString("exEngHidden"));
                        product_lists.add(pr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setupData(product_lists);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
        Log.i("TEST","서버에서 데이터 가져옴");

    }

    private void setupData(ArrayList<Product_List> product_lists) {
        adapter=new ProductAdapter(product_lists, getApplicationContext(),mLinearLayout);
        recyclerView.setAdapter(adapter);
        Log.i("TEST","리싸이클러뷰에 데이터 붙음");
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TEST","MainActivity :onResume 실행");
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
        Log.i("TEST","MainActivity :onDestroy 실행");

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String title = item.getTitle().toString();

        if (id == R.id.nav_home) {
            Toast.makeText(mContext, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(mContext, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(mContext, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_purchase) {
            Toast.makeText(mContext, title + ": 프리미엄 구매", Toast.LENGTH_SHORT).show();
            if (storage.purchasedRemoveAds()) {
                Log.e("IsPremium: ", "Purchase has been already processed!");
            } else {
                bp.purchase(this, getResources().getString(R.string.in_app_premium));
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        // Called if purchase has been successfully processed.
        if (productId.equals(getResources().getString(R.string.in_app_premium))) {
            // Purchase Successful!
            storage.setPurchasedRemoveAds(bp.isPurchased(getResources().getString(R.string.in_app_premium)));
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {
        storage.setPurchasedRemoveAds(bp.isPurchased(getResources().getString(R.string.in_app_premium)));
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        if (errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            Toast.makeText(mContext, "프리미엄 구매중 오류 발생", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBillingInitialized() {
        // Store the purchase history
        storage.setPurchasedRemoveAds(bp.isPurchased(getResources().getString(R.string.in_app_premium)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
