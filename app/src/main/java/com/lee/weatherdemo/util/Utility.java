package com.lee.weatherdemo.util;

import android.bluetooth.le.ScanRecord;
import android.text.TextUtils;

import com.lee.weatherdemo.db.City;
import com.lee.weatherdemo.db.County;
import com.lee.weatherdemo.db.Province;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @类名: ${type_name}
 * @功能描述:
 * @作者: ${user}
 * @时间: ${date}
 * @最后修改者:
 * @最后修改内容:
 */
public class Utility {

    /**
     * 解析处理服务器返回的数据
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject object = allProvince.getJSONObject(i);
                    Province p = new Province();
                    p.setProvinceName(object.getString("name"));
                    p.setProvinceCode(object.getInt("id"));
                    p.save();
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static boolean handleCityResponse(String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCity = new JSONArray(response);
                for (int i = 0; i < allCity.length(); i++) {
                    JSONObject object = allCity.getJSONObject(i);
                    City c = new City();
                    c.setCityName(object.getString("name"));
                    c.setCityCode(object.getInt("id"));
                    c.setProvinceId(provinceId);
                    c.save();
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0; i < allCounty.length(); i++) {
                    JSONObject object = allCounty.getJSONObject(i);
                    County c = new County();
                    c.setCountyName(object.getString("name"));
                    c.setCityId(cityId);
                    c.setWeatherId(object.getString("weather_id"));
                    c.save();
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
}
//jhfghfh