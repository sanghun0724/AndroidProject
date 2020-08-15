package com.example.administrator.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

public class Ceo extends AppCompatActivity {
    private Intent intent;
    private int NUMBER;
    private String NAME;
    private String COMPANY;
    private int BIG;
    private int SMALL;

    private TextView textView1;
    private TextView textView2;
    private ImageView imageView1;
    private ImageView imageView2;
    private TextView containText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceo_man);

        intent=getIntent();
        NUMBER=intent.getIntExtra("NUMBER",-1);
        NAME=intent.getStringExtra("NAME");
        COMPANY=intent.getStringExtra("COMPANY");
        BIG=intent.getIntExtra("BIG",-1);
        SMALL=intent.getIntExtra("SMALL",-1);

        textView1=findViewById(R.id.tex1);
        textView2=findViewById(R.id.tex2);
        imageView1=findViewById(R.id.character);
        imageView2=findViewById(R.id.small_man);
        containText=findViewById(R.id.contain_text);
        containText.setMovementMethod(new ScrollingMovementMethod());

            switch (NUMBER){

            case 0:
                imageView1.setImageResource(BIG);
                imageView2.setImageResource(SMALL);
                textView1.setText(NAME);
                textView2.setText(COMPANY);
                containText.setText(getString(R.string.pichai));
                  break;

            case 1:
            imageView1.setImageResource(BIG);
                imageView2.setImageResource(SMALL);
                textView1.setText(NAME);
                textView2.setText(COMPANY);
                containText.setText(getString(R.string.jobs));

               break;

            case 2:
                imageView1.setImageResource(BIG);
                imageView2.setImageResource(SMALL);
                textView1.setText(NAME);
                textView2.setText(COMPANY);
                containText.setText(getString(R.string.musk));
             break;

                case 3:
                    imageView1.setImageResource(BIG);
                    imageView2.setImageResource(SMALL);
                    textView1.setText(NAME);
                    textView2.setText(COMPANY);
                    containText.setText(getString(R.string.billy));
                    break;
                case 4:
                    imageView1.setImageResource(BIG);
                    imageView2.setImageResource(SMALL);
                    textView1.setText(NAME);
                    textView2.setText(COMPANY);
                    containText.setText(getString(R.string.parker));
                    break;
                case 5:
                    imageView1.setImageResource(BIG);
                    imageView2.setImageResource(SMALL);
                    textView1.setText(NAME);
                    textView2.setText(COMPANY);
                    containText.setText(getString(R.string.cook));
                    break;
                case 6:
                    imageView1.setImageResource(BIG);
                    imageView2.setImageResource(SMALL);
                    textView1.setText(NAME);
                    textView2.setText(COMPANY);
                    containText.setText(getString(R.string.waren));
                    break;

        }



    }
}
