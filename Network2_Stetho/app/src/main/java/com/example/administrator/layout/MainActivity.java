package com.example.administrator.layout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.JsonArray;

import org.json.JSONArray;

//안드로이드에서는 메인쓰레드가 정지될수없기 떄문에 통신은 비동기로 진행 (정지되면 에러나옴) //기로 진행할경우 따로 쓰레드를 파서 그곳에서 진행 해야함
public class MainActivity extends AppCompatActivity {
      TextView textView;
    TextView textView2;
      Service service;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Stetho Debugging address
        //chrome://inspect/#devices //AVD 키거나 폰 연결해주면 디버깅가능한 앱들이 뜸! ㄱㄱ하면댐

        Stetho.initializeWithDefaults(this);
        //스테토는 레트로핏에 끼어들어 레트로핏을 탐색하며 낚아채는형식으로 디버깅(so 레트로핏 사이에 스테토를 넣어줌)
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor())
                .build();//낚싯대 생성 ㅎ


        //gson을원하는 형태로 컨ㅌ버트하기위해 필요
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")//말그대로 베이스가되는url ,/ 이후로는 변경될수도있음  / 뺴먹지 말기
                .client(okHttpClient) //낚싯대 걸기
                .addConverterFactory(GsonConverterFactory.create())
                .build(); //레트로핏 객체 생성

        Service service = retrofit.create(Service.class);
        // 요청한 결과값 받는것 이제 시작 //호출과 반응 타입 동일하게 적어주어야함

        //텍스트뷰 클릭히면 통신하게 버튼만든것
        textView = findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUserRepo(); //밑에 버튼! 통신 시작
            }
        });
        textView2= findViewById(R.id.text2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostUser();
            }
        });
     }



      public void PostUser(){
        Call<JsonArray> PostUser =service.PostUser("changja88",22);
        PostUser.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
      }



       public void requestUserRepo(){
        Call<JsonArray> RequestUserRespo =service.getUserResponsitories("changja88");
            RequestUserRespo.enqueue(new Callback<JsonArray>() { //비동기 방식
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) { //반응이오면 이메소드

                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) { //반응 실패하면 이 메소드

                }
            });

        }



}
