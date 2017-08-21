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
public class Suggestion {
    
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash carWash;
    @SerializedName("sport")
    public Sport sport;
    @SerializedName("air")
    public Air air;
    @SerializedName("drsg")
    public Drsg drsg;
    @SerializedName("flu")
    public Flu flu;
    @SerializedName("trav")
    public Trav trav;
    @SerializedName("uv")
    public Uv uv;
    
    
    
    public class Comfort{
        
        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }

    public class CarWash{

        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }

    public class Sport{

        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    public class Air{

        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    public class Drsg{

        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    public class Flu{

        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    public class Trav{

        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    public class Uv{

        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    
}
//jhfghfh