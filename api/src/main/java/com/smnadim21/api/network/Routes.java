package com.smnadim21.api.network;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Routes {


    @Headers({"Accept: application/json"})
    @GET("api/sub_check")
    Call<String> getStatus(@Query("code") String code);

    @Headers({"Accept: application/json"})
    @GET("download")
    Call<String> subscribe(@Query("app_id") String app_id, @Query("file_path") String file_path);


}


