package com.ogrg.techtown.vocavocca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.snackbar.Snackbar;
import com.ogrg.techtown.vocavocca.common.DefineValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class FavoriteActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private RecyclerView recyclerView;
    private CoordinatorLayout favActivityLayout;
    private FavoriteAdapter adapter;
    Context mConText;
    int showMegIndex = 0;
    boolean isRightHide = false;
    boolean isLeftHide = false;
    List<FavoriteList> favoriteLists;
    View v_hideRight, v_hideLeft, dividerL,dividerR;
    Button btnMean, btnWord, btnMeanLine, btnWordLine;
    private String fileName = "voccaList.csv";
    ArrayList<Product_List> product_lists; //엑셀 파일 추출에 쓰일, 메인 액티비티에서 intent로 받을 array 로 쓰거임

    private SharedPreferences pref;
    private String PREMIUM = "premium";
    private String COUNTER = "counter";
    private int exportCounter;
    private static boolean isRemoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TEST","FavoriteActivity onCreate 실행 : 즐겨찾기 처음 보여짐");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mConText = this;

        favActivityLayout = findViewById(R.id.favActivityLayout);
        recyclerView = findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteLists = MainActivity.favoriteDatabase.favoriteDao().getFavoriteData();


        /*데이터 수신*/
        product_lists = new ArrayList<Product_List>();
        product_lists = getIntent().getParcelableArrayListExtra("productListFromMain");

        Toolbar toolbar = findViewById(R.id.fav_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( FavoriteActivity.this,MainActivity.class));
                finish();
                Log.i("TEST","FavoriteActivity : 창 닫고 홈화면으로 돌아가기" );
            }
        });
        toolbar.setTitle("보관함");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);
        whiteNotificationBar(recyclerView);

        getFavData();
        setFbtn();
        setView();

        pref = getSharedPreferences("AppStorage", Context.MODE_PRIVATE);
        isRemoved = pref.getBoolean(PREMIUM,false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_folder, menu);
        MenuItem folder = menu.findItem(R.id.action_folder);
        folder.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                exportExcel();
                return false;
            }
        });
        return true;
    }

    private void exportExcel() {
        exportCounter = pref.getInt(COUNTER,0);

        if (!isRemoved && exportCounter>=1) {
            //Please purchase premium version!!
            Intent intent = new Intent(getApplicationContext(), purchase_noti.class);
            startActivityForResult(intent, 1);
        }
        else {
            Log.e("favoriteLists", "exportExcel: "+favoriteLists.size());
            if(favoriteLists.size() != 0) {
                makeSnackBar("보관함에 있는 단어를 엑셀파일로 추출하겠습니다.");
                Log.i("TEST","FavoriteActivity : 엑셀파일 추출 버튼 눌림" );
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("yyMMdd");

                Date time = new Date();
                String time1 = format1.format(time);
                String time2 = format2.format(time);
                String timeAddTitle = "voccaList_" + time2;

                StringBuilder data = new StringBuilder();
                data.append("작성 날짜" + "," + time1);
                data.append("\n" + "단어, 뜻, 예문(영어), 예문(뜻), 예문 빈칸 채우기");

                for (int position = 0; position < DefineValue.ALL_ITEM_COUNT; position++) {
                    if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(product_lists.get(position).getId()) == 1) {
                        Product_List productList = product_lists.get(position);

                        String engWord = productList.getEngWord();
                        String korMean = productList.getKorMean();
                        korMean = korMean.replaceAll(",", "/");
                        String korMean2 = productList.getKorMean2();
                        korMean2 = korMean2.replaceAll(",", "/");
                        String exEng = productList.getExEng();
                        String exKor = productList.getExKor();
                        String exEngHidden = productList.getExEngHidden();


                        data.append("\n" + engWord + "," + korMean + " " + korMean2 + "," + exEng + "," + exKor + "," + exEngHidden);
                    }
                }
                try {
                    // saving the file into device
                    FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
                    OutputStreamWriter osw = null;
                    BufferedWriter bfw = null;

//                out.write((data.toString()).getBytes());
//                out.close();

                    osw = new OutputStreamWriter(out, "MS949");
                    bfw = new BufferedWriter(osw);
                    bfw.write((data.toString()));  //쓰기
                    bfw.close();

                    // exporting
                    Context context = getApplicationContext();
                    File filelocation = new File(getFilesDir(), fileName);
                    Uri path = FileProvider.getUriForFile(context, "com.ogrg.techtown.prac_fav.fileprovider", filelocation);
                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    fileIntent.setType("text/csv");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, timeAddTitle);
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(fileIntent, "Send mail"));

                    exportCounter++;
                    pref = getSharedPreferences("AppStorage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt(COUNTER,exportCounter);
                    editor.commit();
                } catch (Exception e) {
                    makeSnackBar("파일 추출에 실패했습니다. 다시 시도해주세요. ");
                    e.printStackTrace();
                }
            }else{
                makeSnackBar("보관함이 비어있습니다. ");
            }
        }
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setView() {
        v_hideRight = findViewById(R.id.coverRight);
        v_hideLeft = findViewById(R.id.coverLeft);
    }

    private void setFbtn() {
        btnWord = findViewById(R.id.btnWord);
        btnMean = findViewById(R.id.btnMean);
        btnMeanLine = findViewById(R.id.btnMean_line);
        btnWordLine = findViewById(R.id.btnWord_line);
        dividerL = findViewById(R.id.dividerL);
        dividerR = findViewById(R.id.dividerR);

        btnWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //(좌) 누르면
                if(!isLeftHide && !isRightHide) {
                    v_hideRight.setVisibility(INVISIBLE);
                    dividerR.setVisibility(INVISIBLE);
                    v_hideLeft.setVisibility(VISIBLE);
                    dividerL.setVisibility(VISIBLE);
                    btnWord.setTextColor(getResources().getColor(R.color.colorAccentGreen));
                    btnWordLine.setBackgroundColor(getResources().getColor(R.color.colorAccentGreen));
                    btnMean.setTextColor(getResources().getColor(R.color.colorLigthtGray));
                    btnMeanLine.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    isLeftHide = true;
                }else if(isLeftHide){
                    initCover();
                }else {
                    closeAllCoverWithMeg();
                }
            }
        });
        btnMean.setOnClickListener(new View.OnClickListener() { //단어보까(우) 누르면
            @Override
            public void onClick(View v) {
                if (!isLeftHide && !isRightHide) {
                    v_hideRight.setVisibility(VISIBLE);
                    v_hideLeft.setVisibility(INVISIBLE);
                    btnWord.setTextColor(getResources().getColor(R.color.colorLigthtGray));
                    btnWordLine.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    btnMean.setTextColor(getResources().getColor(R.color.colorAccentGreen));
                    btnMeanLine.setBackgroundColor(getResources().getColor(R.color.colorAccentGreen));
                    dividerL.setVisibility(INVISIBLE);
                    dividerR.setVisibility(VISIBLE);
                    isRightHide = true;
                } else if (isRightHide) {
                    initCover();
                } else {
                    closeAllCoverWithMeg();
                }
            }
        });
    }

    private void closeAllCoverWithMeg() {
        Snackbar snackbar = Snackbar.make(favActivityLayout, "이미 열려있습니다. ", Snackbar.LENGTH_LONG);
        snackbar.setAction("닫기", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCover();
            }
        });
        snackbar.setActionTextColor(Color.parseColor("#05F26C"));
        snackbar.show();
    }

    private void initCover() {
        v_hideRight.setVisibility(INVISIBLE);
        dividerR.setVisibility(INVISIBLE);
        v_hideLeft.setVisibility(INVISIBLE);
        dividerL.setVisibility(INVISIBLE);
        btnWord.setTextColor(getResources().getColor(R.color.colorLigthtGray));
        btnWordLine.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btnMean.setTextColor(getResources().getColor(R.color.colorLigthtGray));
        btnMeanLine.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        isLeftHide = false;
        isRightHide = false;
    }

    private void getFavData(){
        adapter = new FavoriteAdapter(favoriteLists, mConText);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoriteAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = favoriteLists.get(viewHolder.getAdapterPosition()).getEngWord();

            // backup of removed item for undo purpose
            final FavoriteList deletedItem = favoriteLists.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deletedIndex);
            Log.i("TEST","FavoriteAcitvity : "+ position +"번째 아이템 삭제됨 ");
            showMeg();
        }
    }

    private void showMeg() {
        final CharSequence[] items = { "열심히 공부하는 당신은 짱\uD83D\uDC4D", "Well done! \uD83D\uDE03","\uD83D\uDE4C연속 3개째예요. ", "\uD83D\uDC40 우와와 \uD83D\uDC40 4개!!!!" };

        String snackText = String.valueOf(items[showMegIndex]);
        showMegIndex++;
        makeSnackBar(snackText);
        if(showMegIndex == items.length){
            showMegIndex = 0;
        }
    }

    private void makeSnackBar(String string){
        Snackbar snackbar = Snackbar.make(favActivityLayout, string, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
