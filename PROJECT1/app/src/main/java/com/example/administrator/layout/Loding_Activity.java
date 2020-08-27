//package com.example.administrator.layout;
//
//import android.os.Bundle;
//import android.os.Handler;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class Loding_Activity extends AppCompatActivity {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.loding_activity);
//        start_Loding();
//    }
//
//    public void start_Loding() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();   // 하고싶은 동작  써주세용 ><
//
//            }
//        }, 1000); // 1초 =1000  시간되면  run 메소드 발동!
//
//
//    }
//}
//
