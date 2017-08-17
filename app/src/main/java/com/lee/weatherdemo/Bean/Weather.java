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
    
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    
    
    
}
//jhfghfh