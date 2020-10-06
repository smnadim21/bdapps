package com.smnadim21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shakibaenur.bdapps.R;
import com.smnadim21.api.activity.OtpActivate;

public class ActivateOtp extends OtpActivate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_otp);
    }
}
