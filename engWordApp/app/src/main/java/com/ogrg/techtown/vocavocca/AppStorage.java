package com.ogrg.techtown.vocavocca;

import android.content.Context;
import android.content.SharedPreferences;

public class AppStorage {
    private Context context;
    private SharedPreferences pref;
    private String PREMIUM = "premium";

    public AppStorage(Context context) {
        pref = context.getSharedPreferences("AppStorage", Context.MODE_PRIVATE);
        this.context = context;
    }

    public boolean purchasedRemoveAds() {
        return pref.getBoolean(PREMIUM, false);
    }

    public void setPurchasedRemoveAds(boolean flag) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PREMIUM, flag);
        editor.apply();
    }
}
