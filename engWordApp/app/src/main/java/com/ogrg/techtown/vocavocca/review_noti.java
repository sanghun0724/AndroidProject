package com.ogrg.techtown.vocavocca;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class review_noti extends Activity {

    private Button review_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_noti);

        review_btn = findViewById(R.id.btnReview);

        review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.ogrg.techtown.vovavocca"));
                intent.setPackage("com.android.vending");
                startActivity(intent);
            }
        });

    }
}