package com.example.administrator.layout;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Insert;
import androidx.room.Room;
//context가 필요한 경우 AndroidVIewModel 로 바꿔야함 그래야 context를 application 으로 받을수있음
public class mainViewModel extends AndroidViewModel {
    AppDatabase db;

    public mainViewModel(Application application) {
        super(application);
        db=Room.databaseBuilder(application,AppDatabase.class,"todo-db").build();
    }
    public LiveData<List<Todo>> getAll(){
        return db.todoDao().getAll();    //db를 사용자가 합부로 바꾸지 못하도록 라이브데이터로 한번 감싸줌
    }

    public void insert (Todo todo){
        new InsertAsyncTask(db.todoDao()).execute(todo);
    }





    private static class InsertAsyncTask extends AsyncTask<Todo,Void,Void> {
        private TodoDao mtodoDao;


        public InsertAsyncTask(TodoDao mtodoDao) {
            this.mtodoDao = mtodoDao;
        }
        @Override                     //배열로받음
        protected Void doInBackground(Todo... todos) {
            mtodoDao.insert(todos [0]);
            return null;
        }
    }
}
