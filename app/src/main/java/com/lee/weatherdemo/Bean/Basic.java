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
public class Basic {
    
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    @SerializedName("cnty")
    public String cnty; //国家
    @SerializedName("lat")
    public String lat;  //经度
    @SerializedName("lon")
    public String lon;  //纬度
    
    @SerializedName("updata")
    public Updata updata;
    
    public class Updata{
        
        @SerializedName("loc")
        public String loc;      //最新时间
        @SerializedName("utc")
        public String utc;      //上一次时间
    }
    
    
}
//jhfghfh