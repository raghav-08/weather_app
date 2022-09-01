package com.ashucode.weatherapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weather {
    @GET("weather")
    Call<Example> getweather(@Query("q") String cityname,
                             @Query("appid") String appid);
}
