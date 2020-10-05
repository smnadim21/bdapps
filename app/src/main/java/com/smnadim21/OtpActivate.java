package com.smnadim21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.shakibaenur.bdapps.MainActivity;
import com.shakibaenur.bdapps.R;
import com.smnadim21.api.Constants;
import com.smnadim21.api.listener.SubscriptionStatusListener;

import static com.smnadim21.api.BdApps.sendOtpWithDeviceId;
import static com.smnadim21.api.BdApps.verifyOtp;

public class OtpActivate extends AppCompatActivity implements SubscriptionStatusListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activate);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        Constants.APP_ID = "APP_032283";

        Log.e("data", data != null ? data.toString() : "Empty!");

        if (data != null) {
            Log.e("otp", data.getQueryParameter("otp") != null ? data.getQueryParameter("otp") : "Empty!");
            String code = data.getQueryParameter("otp") != null
                    ? data.getQueryParameter("otp")
                    : "Empty";

            verifyOtp(code, this);

        }

    }

    @Override
    public void onSuccess(boolean isSubscribed) {
        Log.e("onSuccess", isSubscribed + "");
    }

    @Override
    public void onFailed(String message) {
        Log.e("onFailed", message + "");

    }
}
