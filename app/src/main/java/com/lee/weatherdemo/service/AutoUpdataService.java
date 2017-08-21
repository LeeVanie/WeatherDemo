package com.lee.weatherdemo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lee.weatherdemo.Bean.Weather;
import com.lee.weatherdemo.Common;
import com.lee.weatherdemo.WeatherActivity;
import com.lee.weatherdemo.util.HttpUtils;
import com.lee.weatherdemo.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @类名: ${type_name}
 * @功能描述:
 * @作者: ${user}
 * @时间: ${date}
 * @最后修改者:
 * @最后修改内容:
 */
public class AutoUpdataService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updataWeather();
        updataBingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000;
        long tigerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent in = new Intent(this, AutoUpdataService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, in, 0);
        manager.cancel(pendingIntent);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tigerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气信息
     */
    public void updataWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (null == weatherString) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;
            String weatherUrl = Common.URL_WEATHER_CODE + "weather?city=" + weatherId + "&key=" + Common.URL_KEY;
            HttpUtils.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    final Weather weather = Utility.handleWeatherResponse(responseText);
                    if (null != weather && "ok".equalsIgnoreCase(weather.status)) {
                        SharedPreferences.Editor sp = PreferenceManager.getDefaultSharedPreferences(AutoUpdataService
                                .this).edit();
                        sp.putString("weather", responseText);
                        sp.apply();
                    }
                }
            });
        }
    }

    /**
     * 背景图
     */
    public void updataBingPic(){
        String requestPicUrl = "http://guolin.tech/api/bing_pic";
        HttpUtils.sendOkHttpRequest(requestPicUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor sp = PreferenceManager.getDefaultSharedPreferences(AutoUpdataService.this)
                        .edit();
                sp.putString("bing_pic", bingPic);
                sp.apply();
            }
        });
    }
    
    
}
//jhfghfh