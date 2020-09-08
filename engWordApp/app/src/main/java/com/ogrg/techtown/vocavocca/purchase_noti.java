package com.ogrg.techtown.vocavocca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;

public class purchase_noti extends Activity implements BillingProcessor.IBillingHandler{
    //In-App purchase
    private BillingProcessor bp;
    private AppStorage storage;
    private Context mContext;

    private Button btnPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_noti);

        btnPurchase = findViewById(R.id.btnPurchase);

        bp = new BillingProcessor(this, getResources().getString(R.string.in_app_RSA), this);
        bp.initialize();
        storage = new AppStorage(this);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storage.purchasedRemoveAds()) {
                    Log.e("IsPremium: ", "Purchase has been already processed!");
                } else {
                    bp.purchase(purchase_noti.this, getResources().getString(R.string.in_app_premium));
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        // Called if purchase has been successfully processed.
        if (productId.equals(getResources().getString(R.string.in_app_premium))) {
            // Purchase Successful!
            storage.setPurchasedRemoveAds(bp.isPurchased(getResources().getString(R.string.in_app_premium)));
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {
        storage.setPurchasedRemoveAds(bp.isPurchased(getResources().getString(R.string.in_app_premium)));
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        if (errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            Toast.makeText(mContext, "프리미엄 구매중 오류 발생", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBillingInitialized() {
        // Store the purchase history
        storage.setPurchasedRemoveAds(bp.isPurchased(getResources().getString(R.string.in_app_premium)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
