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
    
    public Updata updata;
    
    public class Updata{
        
        @SerializedName("loc")
        public String updataTime;
    }
    
    
}
//jhfghfh