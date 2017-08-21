package com.lee.weatherdemo.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * @类名: ${type_name}
 * @功能描述:
 * @作者: ${user}
 * @时间: ${date}
 * @最后修改者:
 * @最后修改内容:
 */
public class AQI {
    
    @SerializedName("city")
    public AQICity city;
    
    public class AQICity{
        @SerializedName("aqi")
        public String aqi;
        @SerializedName("pm25")
        public String pm25;
        @SerializedName("pm10")
        public String pm10;
        @SerializedName("qlty")
        public String qlty;
    }
    
}
//jhfghfh