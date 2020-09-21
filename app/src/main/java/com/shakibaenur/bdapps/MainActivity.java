package com.shakibaenur.bdapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.SuccessActivity;
import com.smnadim21.api.BdApps;
import com.smnadim21.api.Constants;
import com.smnadim21.api.SubscriptionStatusListener;

public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener {

    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.MSG_TEXT = "start 123sa";
        Constants.APP_ID = "APP_016475";
        Constants.APP_PASSWORD = "f36f24ba800203e608718261e2d7d725";
        BdApps.registerAPP();
        BdApps.checkSubscriptionStatus(this);

    }

    @Override
    public void onSuccess(boolean isSubscribed) {
        if (!isSubscribed) {
            flag = false;
        }
        else {
            flag = true;
        }
    }

    @Override
    public void onFailed(String message) {

    }

    public void CheckPaymentApi(View view) {
        if(!flag)
        {
            BdApps.showDialog(this, MainActivity.this);
        }else{
            startActivity(new Intent(this, SuccessActivity.class));
        }
    }
}