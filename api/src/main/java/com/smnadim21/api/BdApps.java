package com.smnadim21.api;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.smnadim21.api.listener.AdidListener;
import com.smnadim21.api.listener.PurchaseStatusListener;
import com.smnadim21.api.listener.SubscriptionStatusListener;
import com.smnadim21.api.model.CheckStatusModel;
import com.smnadim21.api.network.ApiClient;
import com.smnadim21.api.tools.GetAdvId;
import com.smnadim21.api.tools.JsonParser;
import com.smnadim21.api.tools.Toaster;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BdApps extends Constants implements JsonParser {

    public static void registerAPP() {
        ApiClient
                .getStringInstance()
                .register(APP_ID, APP_PASSWORD)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e("registercheck", "reg" + response.toString());

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("registercheck", "failed" + t.getMessage());
                    }
                });
    }


    public static void showDialog(final Activity activity, final SubscriptionStatusListener statusListener) {
        GetAdvId.getAdId((boolean isSuccess, String data) -> {
            if (isSuccess) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
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
                                    Toaster.ShowLogToast("Write a valid code");
                                } else {

                                    sendOtpWithDeviceId(dialog, getCode.getText().toString(), data, statusListener);


                                }

                            }
                        });

                if (!flag) {
                    dialog.show();
                }
            } else {
                Toaster.ShowLogToast("" + data);
            }
        });

    }


    public static void showDialogUSSD(final Activity activity, final SubscriptionStatusListener statusListener) {
        GetAdvId.getAdId((boolean isSuccess, String data) -> {
            if (isSuccess) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.sub);


                final EditText getCode = dialog.findViewById(R.id.otp_code);


                Button dialogButton = (Button) dialog.findViewById(R.id.button_s_daily);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Uri uri = Uri.parse("tel:*213*" + USSD + Uri.encode("#"));
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
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
                                    Toaster.ShowLogToast("Write a valid code");
                                } else {

                                    sendOtpWithDeviceId(dialog, getCode.getText().toString(), data, statusListener);


                                }

                            }
                        });

                if (!flag) {
                    dialog.show();
                }
            } else {
                Toaster.ShowLogToast("" + data);
            }
        });

    }


    //shakiba
    private static boolean flag = false;

    public static void checkSubscriptionStatus(final SubscriptionStatusListener statusListener) {


        GetAdvId.getAdId((isSuccess, data) -> {
            if (isSuccess) {
                ApiClient
                        .getStringInstance()
                        .checkSubStatus(APP_ID, data)
                        .enqueue(new Callback<CheckStatusModel>() {
                            @Override
                            public void onResponse(Call<CheckStatusModel> call, Response<CheckStatusModel> response) {
                                Log.e("responsenew", response.toString());

                                if (response.body() != null) {

                                    CheckStatusModel checkStatusModel = response.body();

                                    if (statusListener != null) {
                                        if (checkStatusModel.getStatus() == TypeCode.RESPONSE_OK) {
                                            if (checkStatusModel.getData() != null) {
                                                if (checkStatusModel.getData().getStatus().equals(TypeStatus.STATUS_REGISTED)) {
                                                    statusListener.onSuccess(true);
                                                    Toaster.ShowLogToast("Subscribed!");
                                                    flag = true;
                                                    return;
                                                }
                                            }

                                        }
                                    }
                                    if (statusListener != null) {
                                        statusListener.onSuccess(false);
                                    }
                                    Toaster.ShowLogToast("not subscribed!");
                                    flag = false;

                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                if (statusListener != null) {
                                    statusListener.onFailed("" + t.getMessage());

                                    Toaster.ShowLogToast("" + t.getMessage());
                                }
                                Log.e("responsenew", t.getMessage());

                            }
                        });
            } else {
                Toaster.ShowLogToast("" + data);
            }
        });

    }

    public static void sendOtpWithDeviceId(Dialog dialog, String code, String deviceId, final SubscriptionStatusListener statusListener) {
        ApiClient
                .getStringInstance()
                .sumitOtpwithDevice(code, deviceId)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e("response", "reg" + response.toString());
                        boolean isActive = Boolean.parseBoolean("" + response.body().get("isActive"));
                        if (isActive) {//valid otp
                            checkSubscriptionStatus(statusListener);
                            dialog.dismiss();
                        } else {
                            Toaster.ShowLogToast("Invalid OTP");
                            // dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toaster.ShowLogToast("Otp Failed:" + t.getMessage());
                        // dialog.dismiss();
                    }
                });
    }


    //author: smnadim21
    public static void verifyOtp(String code, Activity activity) {
        GetAdvId.getAdId(new AdidListener() {
            @Override
            public void onSuccess(boolean isSuccess, String data) {
                if (isSuccess) {
                    ApiClient
                            .connect()
                            .verifyOtp(APP_ID, code, data)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.e("verifyOtp", response.toString());
                                    if (response.body() != null) {
                                        Log.e("verifyOtp", response.body());
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body());
                                            if (jsonObject.optBoolean(VerifyOtp.valid_subscriber, false)) {

                                                if (activity != null) {
                                                    if (activity instanceof SubscriptionStatusListener) {
                                                        ((SubscriptionStatusListener) activity).onSuccess(jsonObject.getBoolean(VerifyOtp.valid_subscriber));
                                                        showSnackbarWithColor(activity,
                                                                jsonObject.optString(VerifyOtp.message, "no message"),
                                                                "OK",
                                                                Snackbar.LENGTH_INDEFINITE,
                                                                new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                    }
                                                                },
                                                                R.color.green);
                                                    } else {
                                                        showSnackbarWithColor(activity,
                                                                "SubscriptionStatusListener is not implemented on Activity properly",
                                                                "OK",
                                                                Snackbar.LENGTH_INDEFINITE,
                                                                new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                    }
                                                                },
                                                                R.color.red);
                                                    }

                                                }
                                            } else if (jsonObject.optBoolean(VerifyOtp.existing_subscriber, false)) {
                                                showSnackbarWithColor(activity,
                                                        jsonObject.optString(VerifyOtp.message, "no message"),
                                                        "SUBSCRIBE",
                                                        Snackbar.LENGTH_INDEFINITE,
                                                        new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        },
                                                        R.color.red);

                                            } else {
                                                showSnackbarWithColor(activity,
                                                        jsonObject.optString(VerifyOtp.message, "no message"),
                                                        "OK",
                                                        Snackbar.LENGTH_INDEFINITE,
                                                        new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        },
                                                        R.color.red);

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            showSnackbarWithColor(activity,
                                                    e.getMessage(),
                                                    "SUBSCRIBE",
                                                    Snackbar.LENGTH_INDEFINITE,
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                        }
                                                    },
                                                    R.color.red);

                                        }

                                    }


                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("verifyOtp", t.getMessage());

                                    showSnackbarWithColor(activity,
                                            t.getMessage(),
                                            "OK",
                                            Snackbar.LENGTH_INDEFINITE,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            },
                                            R.color.red);

                                }
                            });
                }
            }
        });
    }

    //author smnadim21
    private static void getSubscriptionStatus(String code, Activity activity) {
        GetAdvId.getAdId(new AdidListener() {
            @Override
            public void onSuccess(boolean isSuccess, String data) {
                if (isSuccess) {
                    ApiClient
                            .connect()
                            .verifyOtp(APP_ID, code, data)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.e("verifyOtp", response.toString());
                                    if (response.body() != null) {
                                        Log.e("verifyOtp", response.body());
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body());
                                            if (jsonObject.optBoolean(VerifyOtp.valid_subscriber, false)) {

                                                if (activity != null) {
                                                    if (activity instanceof SubscriptionStatusListener) {
                                                        ((SubscriptionStatusListener) activity).onSuccess(jsonObject.getBoolean(VerifyOtp.valid_subscriber));
                                                        showSnackbarWithColor(activity,
                                                                jsonObject.optString(VerifyOtp.message, "no message"),
                                                                "OK",
                                                                Snackbar.LENGTH_INDEFINITE,
                                                                new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                    }
                                                                },
                                                                R.color.green);
                                                    } else {
                                                        showSnackbarWithColor(activity,
                                                                "SubscriptionStatusListener is not implemented on Activity properly",
                                                                "OK",
                                                                Snackbar.LENGTH_INDEFINITE,
                                                                new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                    }
                                                                },
                                                                R.color.red);
                                                    }

                                                }
                                            } else if (jsonObject.optBoolean(VerifyOtp.existing_subscriber, false)) {
                                                showSnackbarWithColor(activity,
                                                        jsonObject.optString(VerifyOtp.message, "no message"),
                                                        "SUBSCRIBE",
                                                        Snackbar.LENGTH_INDEFINITE,
                                                        new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        },
                                                        R.color.red);

                                            } else {
                                                showSnackbarWithColor(activity,
                                                        jsonObject.optString(VerifyOtp.message, "no message"),
                                                        "OK",
                                                        Snackbar.LENGTH_INDEFINITE,
                                                        new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        },
                                                        R.color.red);

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            showSnackbarWithColor(activity,
                                                    e.getMessage(),
                                                    "SUBSCRIBE",
                                                    Snackbar.LENGTH_INDEFINITE,
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                        }
                                                    },
                                                    R.color.red);

                                        }

                                    }


                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("verifyOtp", t.getMessage());

                                    showSnackbarWithColor(activity,
                                            t.getMessage(),
                                            "OK",
                                            Snackbar.LENGTH_INDEFINITE,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            },
                                            R.color.red);

                                }
                            });
                }
            }
        });
    }

    //authhor: smnadim21
    public static void requestCAAS(String amount, String item_code, Activity activity) {

        GetAdvId.getAdId(new AdidListener() {
            @Override
            public void onSuccess(boolean isSuccess, String data) {
                if (isSuccess) {
                    ApiClient
                            .connect()
                            .requestCaasCharging(
                                    amount,
                                    APP_ID,
                                    item_code,
                                    data
                            ).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.e("requestCAAS", response.toString());

                            if (response.body() != null) {
                                Log.e("verifyOtp", response.body());

                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());

                                    if (activity != null) {
                                        if (activity instanceof PurchaseStatusListener) {
                                            if (jsonObject.optBoolean(RequestCAAS.payment_status, false)) {
                                                ((PurchaseStatusListener) activity)
                                                        .onPaymentSuccess(
                                                                jsonObject.getBoolean(RequestCAAS.payment_status),
                                                                jsonObject.getString(RequestCAAS.message),
                                                                item_code);

                                                if (jsonObject.optBoolean(RequestCAAS.new_payment, false)) {
                                                    showSnackbarWithColor(activity,
                                                            jsonObject.optString(RequestCAAS.message, "no message"),
                                                            "OK",
                                                            Snackbar.LENGTH_INDEFINITE,
                                                            new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {

                                                                }
                                                            },
                                                            R.color.green);
                                                }
                                            } else {
                                                ((PurchaseStatusListener) activity)
                                                        .onPaymentFailed(jsonObject.getString(RequestCAAS.message));
                                                showSnackbarWithColor(activity,
                                                        jsonObject.optString(RequestCAAS.message, "no message"),
                                                        "OK",
                                                        Snackbar.LENGTH_INDEFINITE,
                                                        new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        },
                                                        R.color.red);

                                            }

                                        } else {
                                            showSnackbarWithColor(activity,
                                                    "PurchaseStatusListener is not implemented on Activity properly",
                                                    "OK",
                                                    Snackbar.LENGTH_INDEFINITE,
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                        }
                                                    },
                                                    R.color.red);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    showSnackbarWithColor(activity,
                                            e.getMessage(),
                                            "OK",
                                            Snackbar.LENGTH_INDEFINITE,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            },
                                            R.color.red);
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                            Log.e("requestCAAS", t.getMessage());

                            showSnackbarWithColor(activity,
                                    t.getMessage(),
                                    "OK",
                                    Snackbar.LENGTH_INDEFINITE,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    },
                                    R.color.red);

                        }
                    });
                }
            }
        });

    }

    //author smnadim21 === simplified
    public static void subscribeWithSMS(final Activity activity) {

        GetAdvId.getAdId((boolean isSuccess, String data) -> {
            if (isSuccess) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
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
                                    Toaster.ShowLogToast("Write a valid code");
                                } else {
                                    verifyOtp(getCode.getText().toString(), activity);
                                    dialog.dismiss();
                                }

                            }
                        });
                dialog.show();
            } else {
                Toaster.ShowLogToast("" + data);
            }
        });

    }

    //author smnadim21 === simplified
    public static void subscribeWithUSSD(final Activity activity) {
        GetAdvId.getAdId((boolean isSuccess, String data) -> {
            if (isSuccess) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.sub);


                final EditText getCode = dialog.findViewById(R.id.otp_code);


                Button dialogButton = (Button) dialog.findViewById(R.id.button_s_daily);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Uri uri = Uri.parse("tel:*213*" + USSD + Uri.encode("#"));
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
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
                                    Toaster.ShowLogToast("Write a valid code");
                                } else {
                                    verifyOtp(getCode.getText().toString(), activity);
                                    dialog.dismiss();
                                }

                            }
                        });
                dialog.show();
            } else {
                Toaster.ShowLogToast("" + data);
            }
        });

    }


    public static void showSnackbarWithColor(Activity activity,
                                             final String mainTextString,
                                             final String actionString,
                                             int LENGTH,
                                             View.OnClickListener listener,
                                             int color) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextString,
                LENGTH)
                .setActionTextColor(Color.WHITE)
                .setAction(actionString, listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, color));
        snackbar.show();
    }

}
