package com.lee.weatherdemo.Bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * @类名: ${type_name}
 * @功能描述:
 * @作者: ${user}
 * @时间: ${date}
 * @最后修改者:
 * @最后修改内容:
 */
public class Hourly {
    
    @SerializedName("date")
    public String data;
    @SerializedName("tmp")
    public String tmp;
    @SerializedName("cond")
    public Cond cond;
    @SerializedName("wind")
    public Wind wind;

    
    public class Cond{
        @SerializedName("code")
        public String code;
        @SerializedName("txt")
        public String txt;
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