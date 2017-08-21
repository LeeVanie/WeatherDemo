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
public class Now {
    
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;
    @SerializedName("wind")
    public Wind wind;
    
    
    public class More{
        
        @SerializedName("txt")
        public String info;
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