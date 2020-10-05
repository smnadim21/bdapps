package com.shakibaenur.bdapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.SuccessActivity;
import com.smnadim21.api.BdApps;
import com.smnadim21.api.Constants;
import com.smnadim21.api.listener.PurchaseStatusListener;
import com.smnadim21.api.listener.SubscriptionStatusListener;

public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener, PurchaseStatusListener {

    boolean flag = false;
    private final String ITEM_1 = "APP_031937-0001";
    private final String ITEM_2 = "APP_031937-0000002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.MSG_TEXT = "start clsmt";
        Constants.APP_ID = "APP_031937"; // APP_031937 ,  APP_032283
        Constants.USSD = "2346";
        Constants.APP_PASSWORD = "0a949613a18c0ded793a6b080126844f"; // 0a949613a18c0ded793a6b080126844f , 9f14a1d23262b6c8e7c67fa43779f1e5
        BdApps.registerAPP();
        BdApps.checkSubscriptionStatus(this);


        findViewById(R.id.caas1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BdApps.requestCAAS("1", ITEM_1, MainActivity.this);
                    }
                });

        findViewById(R.id.caas2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BdApps.requestCAAS("2", ITEM_2, MainActivity.this);
                    }
                });


    }

    @Override
    public void onSuccess(boolean isSubscribed) {
        flag = isSubscribed;
    }

    @Override
    public void onFailed(String message) {

    }

    public void CheckPaymentApi(View view) {

        //TODO smnadim21 implement USSD this way

        if (!flag) {
            BdApps.subscribeWithSMS(MainActivity.this);
        } else {
            startActivity(new Intent(this, SuccessActivity.class));
        }
    }

    @Override
    public void onPaymentSuccess(boolean paymentStats, String message, String item_code) {
        Log.e("onPaymentSuccess", paymentStats + " for " + item_code + " " + message);

        switch (item_code)
        {
            case ITEM_1:
            {
                //todo  item_1 stuffs here
                Toast.makeText(MainActivity.this, paymentStats + " for ITEM_1 " + item_code + " " + message, Toast.LENGTH_LONG).show();
                break;
            }
            case ITEM_2:
            {
                //todo  item_2 stuffs here
                Toast.makeText(MainActivity.this, paymentStats + " for ITEM_2 " + item_code + " " + message, Toast.LENGTH_LONG).show();

                break;
            }
            default:
            {
                // unanted items here
            }
        }
    }

    @Override
    public void onPaymentFailed(String message) {
        Log.e("onPaymentFailed", " " + message);
    }
}