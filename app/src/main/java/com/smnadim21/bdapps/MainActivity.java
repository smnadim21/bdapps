package com.smnadim21.bdapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.smnadim21.api.BdApps;
import com.smnadim21.api.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.MSG_TEXT = "Start AbDul";

        BdApps.checkSubStatus();

        findViewById(R.id.check)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BdApps.showDialog(MainActivity.this);
                    }
                });


    }
}
