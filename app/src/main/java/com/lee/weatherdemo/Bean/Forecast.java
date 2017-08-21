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
public class Forecast {
    
    @SerializedName("date")
    public String data;
    
    @SerializedName("tmp")
    public Temperature temperature;
    
    @SerializedName("cond")
    public More more;
    
    @SerializedName("wind")
    public Wind wind;
    
    public class Temperature{
        @SerializedName("max")
        public String max;
        @SerializedName("min")
        public String min;
    }
    
    public class More{
        
        @SerializedName("txt_d")
        public String info;
        @SerializedName("txt_n")
        public String info2;
    }

    public class Wind{
        @SerializedName("deg")
        public String deg;
        @SerializedName("dir")
        public String dir;  //风向
        @SerializedName("sc")
        public String sc;  //风力
        @SerializedName("spd")
        public String spd;  //风速
        
    }
}
//jhfghfh