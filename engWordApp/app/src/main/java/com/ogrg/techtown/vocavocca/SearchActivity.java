package com.ogrg.techtown.vocavocca;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ogrg.techtown.vocavocca.common.DefineValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String HI = "https://engvocaapp.000webhostapp.com/wordfile.php";
    private ArrayList<Product_List> product_lists;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Context mContext;
    private SearchAdapter adapter;
    private CoordinatorLayout mLinearLayout;
    private LinearLayoutManager linearLayoutManager;

    // 데이터
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    public static FavoriteDatabase favoriteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TEST","SearchActivity : onCreate 실행");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

      //Test
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("검색");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( SearchActivity.this,MainActivity.class));
                finish();
                Log.i("TEST","FavoriteActivity : 창 닫고 홈화면으로 돌아가기" );
            }
        });

        // toolbar fancy stuff
        setInitView();

        setInitRecyclerView();
        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();
        getData();
        whiteNotificationBar(recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                Log.i("TEST","MainActivity : 검색 시작" + DefineValue.PREFERENCE_LAST_PAGE_VALUE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.i("TEST","MainActivity : 검색 엑스 버튼");
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
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
        mLinearLayout = findViewById(R.id.MainLayout);
    }

    private void setInitRecyclerView() {
        // 리싸이클러뷰 기본 설정
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
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
        adapter = new SearchAdapter(product_lists, getApplicationContext(), mLinearLayout);
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
        super.onDestroy();
        Log.i("TEST","MainActivity :onDestroy 실행");

    }
}
