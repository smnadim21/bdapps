package com.smnadim21.api;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smnadim21.api.network.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.smnadim21.api.Subscription.getSubCode;
import static com.smnadim21.api.Subscription.setSubCode;
import static com.smnadim21.api.Subscription.setSubscriptionClicked;
import static com.smnadim21.api.Subscription.setSubscriptionStatus;


public class BdApps extends Constants {

    public static void checkSubStatus(String code) {


        ApiClient
                .getStringInstance()
                .getStatus(code)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Log.e("response", response.toString());
                        if (response.body() != null) {
                            try {
                                JSONObject staus = new JSONObject(response.body());

                                if (staus.has(is_there) && staus.getBoolean(is_there)) {
                                    setSubscriptionStatus(false);
                                    toast("Subscription Status True");
                                } else {
                                    setSubscriptionStatus(true);
                                    toast("not a valid subscriber");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                toast("not a valid subscriber");
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                        Log.e("response", t.getMessage());
                        setSubscriptionStatus(true);
                        toast("Status Getting Failed");
                    }
                });

    }


    public static void checkSubStatus() {

        toast("Checking....");

        ApiClient
                .getStringInstance()
                .getStatus(getSubCode())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Log.e("response", response.toString());

                        if (response.body() != null) {
                            try {
                                JSONObject staus = new JSONObject(response.body());

                                if (staus.has(is_there) && staus.getBoolean(is_there)) {
                                    setSubscriptionStatus(false);
                                    toast("Subscription Status True");
                                } else {
                                    setSubscriptionStatus(true);
                                    toast("not a valid subscriber");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                toast("not a valid subscriber");
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                        Log.e("response", t.getMessage());
                        setSubscriptionStatus(true);
                        toast("Status Getting Failed");
                        // Toast.makeText(AppConfig.getContext(), "Status Getting Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public static void subscribe() {
        ApiClient.getStringInstance()
                .subscribe(APP_ID, APP_PATH)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("response", response.toString());

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("error", t.toString());

                    }
                });
    }


    public static void toast(String message) {
        Toast.makeText(BdAppsApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public static void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.sub);


        final EditText getCode = dialog.findViewById(R.id.otp_code);


        Button dialogButton = (Button) dialog.findViewById(R.id.button_s_daily);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse("smsto:21213");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", Constants.MSG_TEXT);
                activity.startActivity(intent);
                dialog.dismiss();

            }
        });


        dialog.findViewById(R.id.submit_code)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getCode.getText().toString().isEmpty()) {
                            Toast.makeText(activity, "Write a valid code", Toast.LENGTH_SHORT).show();
                        } else {
                            setSubCode(getCode.getText().toString());
                            setSubscriptionClicked(false);
                            checkSubStatus(getCode.getText().toString());
                            dialog.dismiss();
                        }

                    }
                });


        dialog.show();

    }


}
