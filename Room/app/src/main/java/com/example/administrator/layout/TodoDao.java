package com.example.administrator.layout;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM Todo")
    LiveData <List<Todo>>getAll();  // todo의 데이터를 수정하기위해  todo 데이터를 다받아옴
  //라이브 데이터는 lifecylcle 안에있고 데이터를 관찰가능하게 해줌
    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

}
