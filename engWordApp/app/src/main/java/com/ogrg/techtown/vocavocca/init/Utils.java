package com.ogrg.techtown.vocavocca.init;

import android.app.Activity;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.ogrg.techtown.vocavocca.R;

public class Utils {
    public enum StatusBarColorType {
        BLACK_STATUS_BAR( R.color.colorBlack ),
        WHITE_STATUS_BAR( R.color.colorWhite ),
        BLUE_STATUS_BAR( R.color.colorAccentBule );

        private int backgroundColorId;

        StatusBarColorType(int backgroundColorId){
            this.backgroundColorId = backgroundColorId;
        }

        public int getBackgroundColorId() {
            return backgroundColorId;
        }
    }

    public static void setStatusBarColor(Activity activity, StatusBarColorType colorType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, colorType.getBackgroundColorId()));
        }
    }
}
