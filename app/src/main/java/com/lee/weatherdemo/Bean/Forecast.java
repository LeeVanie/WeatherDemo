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
    
    public String data;
    
    @SerializedName("tmp")
    public Temperature temperature;
    
    @SerializedName("cond")
    public More more;
    
    
    public class Temperature{
        
        public String max;
        public String min;
    }
    
    public class More{
        
        @SerializedName("txt_d")
        public String info;
    }
    
    
}
//jhfghfh