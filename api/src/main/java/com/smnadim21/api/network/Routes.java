package com.smnadim21.api.network;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Routes {


    @Headers({"Accept: application/json"})
    @GET("api/sub_check")
    Call<String> getStatus(@Query("code") String code);

    @Headers({"Accept: application/json"})
    @GET("download")
    Call<String> subscribe(@Query("app_id") String app_id, @Query("file_path") String file_path);


    @Headers({"Accept: application/json"})
    @POST("api/reg?pwd=bdapps2019")
    Call<String> register(@Query("app_id") String app_id, @Query("password") String password);

    //bdapps.b-rain.com.au/api/reg?pwd=bdapps2019&app_id=APP_020858&password=0dc33105a73479189855ae41b1cef672


}


