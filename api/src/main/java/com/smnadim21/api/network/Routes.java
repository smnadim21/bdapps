package com.smnadim21.api.network;


import com.google.gson.JsonObject;
import com.smnadim21.api.model.CheckStatusModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Routes {
    @Headers({"Accept: application/json"})
    @POST("api/reg?pwd=bdapps2019")
    Call<JsonObject> register(@Query("app_id") String app_id, @Query("password") String password);


    //shakiba integration
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("api/submit_otp")
    Call<JsonObject> sumitOtpwithDevice(@Field("code") String code, @Field("device_id") String device_id);

    @Headers({"Accept: application/json"})
    @GET("api/checkSubscriptionStatus")
    Call<CheckStatusModel> checkSubStatus(@Query("app_id") String app_id, @Query("device_id") String device_id);

    @Headers({"Accept: application/json"})
    @POST("api/verify_otp")
    Call<String> verifyOtp(@Query("app_id") String app_id,
                           @Query("code") String code,
                           @Query("device_id") String device_id
    );


    @Headers({"Accept: application/json"})
    @POST("api/verify_otp")
    Call<String> verifyDevice(@Query("app_id") String app_id,
                           @Query("device_id") String device_id
    );

    //http://127.0.0.1:8000/api/verify_otp?device_id=e94099c3-0401-4b2b-ab1f-3a328cbd3a59&code=XSIU0J&app_id=APP_016475  @Headers({"Accept: application/json"})


    @Headers({"Accept: application/json"})
    @POST("api/caas")
    Call<String> requestCaasCharging(@Query("amount") String amount,
                                     @Query("app_id") String app_id,
                                     @Query("item_code") String code,
                                     @Query("device_id") String device_id
    );

    // {{base}}/api/caas?amount=5&device_id=qwerty&app_id=APP_031937&item_code=APP_031937-0000002


    //bdapps.b-rain.com.au/api/reg?pwd=bdapps2019&app_id=APP_020858&password=0dc33105a73479189855ae41b1cef672


}


