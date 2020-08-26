package com.example.administrator.layout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText mTodoEditText;
    private TextView mResultTextView;
    protected void onCreate(Bundle savedInstanceState) {
        //UI 인터페이스 적인 부분은 viewmodel x db적인 부분만 주로 넣음
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTodoEditText=findViewById(R.id.editText);
        mResultTextView=findViewById(R.id.textView2);
//        final AppDatabase db= Room.databaseBuilder(this,AppDatabase.class,"todo-db")
//                 //실무에서는 백그라운드에서 작동해야 되는데 배우는건깐 메인에서 돌아갈수있게 ^^
//                .build();
//
        final mainViewModel viewModel= ViewModelProviders.of(this).get(mainViewModel.class);//  viewmodel 가져오는법
        //UI 갱신
        viewModel.getAll().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                mResultTextView.setText(todos.toString());
            }
        });

        //버튼 클릭시  db에 insert
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.insert(new Todo(mTodoEditText.getText().toString())); //Edit 에 입력한 것 db에 저장

            }
        });
    }
//    private static class InsertAsyncTask extends AsyncTask<Todo,Void,Void>{
//        private TodoDao mtodoDao;
//
//        public InsertAsyncTask(TodoDao mtodoDao) {
//            this.mtodoDao = mtodoDao;
//        }
//
//        @Override                     //배열로받음
//        protected Void doInBackground(Todo... todos) {
//            mtodoDao.insert(todos [0]);
//            return null;
//        }
//    }

}
