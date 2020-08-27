package com.example.administrator.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public PreferenceManager(){};
    public static void setinSharedPreference(Context context,String key,int value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(key,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();  //기본세팅 인거같음 에디터랑 모드 켜주기 //변경되는값 메소드   (((INT)))
    }
    public static int getinSharedpreference(Context context,String key,int defaultValue){ //defaultValue 기초세팅값  (((INT)))
         SharedPreferences sharedPreferences=context.getSharedPreferences(key,context.MODE_PRIVATE);
         return sharedPreferences.getInt(key,defaultValue);
    }

    public static void setStringPreference(Context context, String key, String value)     //STRING
    {
        SharedPreferences preference = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editPreference = preference.edit();
        editPreference.putString(key, value);
        editPreference.commit();
    }

    public static String getStringPreference(Context context, String key, String defaultValue) //STRING
    {
        SharedPreferences preference = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return preference.getString(key, defaultValue);
    }
}
