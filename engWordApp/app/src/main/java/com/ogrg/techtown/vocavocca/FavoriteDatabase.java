package com.ogrg.techtown.vocavocca;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import static androidx.room.Room.databaseBuilder;

@Database(entities = {FavoriteList.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();

    private static FavoriteDatabase INSTANCE;

    public static FavoriteDatabase getDatabase(final Context context) {
        if(INSTANCE ==null) {
            //동시에 2개의 INSTANCE가 생성되는 것을 막기위한 synchronized
            synchronized (FavoriteDatabase.class) {
                if(INSTANCE==null) {
                    //데이터 베이스 생성부분
                    INSTANCE = databaseBuilder(context.getApplicationContext(),
                            FavoriteDatabase.class,"word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
