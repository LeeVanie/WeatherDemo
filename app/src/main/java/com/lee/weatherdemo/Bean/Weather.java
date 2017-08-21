package com.lee.weatherdemo.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @类名: ${type_name}
 * @功能描述:
 * @作者: ${user}
 * @时间: ${date}
 * @最后修改者:
 * @最后修改内容:
 */
public class Weather {

    @SerializedName("status")
    public String status;
    @SerializedName("basic")
    public Basic basic;
    @SerializedName("aqi")
    public AQI aqi;
    @SerializedName("now")
    public Now now;
    @SerializedName("suggestion")
    public Suggestion suggestion;
    
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    
    
    
}
//jhfghfh