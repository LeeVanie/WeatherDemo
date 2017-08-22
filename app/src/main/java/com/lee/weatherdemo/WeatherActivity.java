package com.lee.weatherdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lee.weatherdemo.Bean.Forecast;
import com.lee.weatherdemo.Bean.Hourly;
import com.lee.weatherdemo.Bean.Weather;
import com.lee.weatherdemo.service.AutoUpdataService;
import com.lee.weatherdemo.util.HttpUtils;
import com.lee.weatherdemo.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
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
public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.title_city)
    TextView titleCity;
    @BindView(R.id.title_updata_time)
    TextView titleUpdataTime;
    @BindView(R.id.degree_text)
    TextView degreeText;
    @BindView(R.id.weather_info_text)
    TextView weatherInfoText;
    @BindView(R.id.forecast_layout)
    LinearLayout forecastLayout;
    @BindView(R.id.aqi_text)
    TextView aqiText;
    @BindView(R.id.pm25_text)
    TextView pm25Text;
    @BindView(R.id.comfort_text)
    TextView comfortText;
    @BindView(R.id.car_wash_text)
    TextView carWashText;
    @BindView(R.id.sport_text)
    TextView sportText;
    @BindView(R.id.weather_layout)
    ScrollView weatherLayout;
    @BindView(R.id.bing_pic_img)
    ImageView bingPicImg;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.nav_button)
    Button navButton;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.weather_wind_sc)
    TextView weatherWindSc;
    @BindView(R.id.air_brf)
    TextView airBrf;
    @BindView(R.id.air_text)
    TextView airText;
    @BindView(R.id.comfort_brf)
    TextView comfortBrf;
    @BindView(R.id.car_wash_brf)
    TextView carWashBrf;
    @BindView(R.id.sport_brf)
    TextView sportBrf;
    @BindView(R.id.drsg_brf)
    TextView drsgBrf;
    @BindView(R.id.drsg_text)
    TextView drsgText;
    @BindView(R.id.flu_brf)
    TextView fluBrf;
    @BindView(R.id.flu_text)
    TextView fluText;
    @BindView(R.id.trav_brf)
    TextView travBrf;
    @BindView(R.id.trav_text)
    TextView travText;
    @BindView(R.id.uv_brf)
    TextView uvBrf;
    @BindView(R.id.uv_text)
    TextView uvText;
    @BindView(R.id.line_chart)
    LineChartView lineChart;
    @BindView(R.id.aqi_zhiliang)
    TextView aqiZhiliang;

    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        if (Build.VERSION.SDK_INT >= 21) {
        //            View dicorView = getWindow().getDecorView();
        //            dicorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
        // .SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //            getWindow().setStatusBarColor(Color.TRANSPARENT);
        //        }
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        initEvent();

    }

    public void initEvent() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String bingPic = sp.getString("bing_pic", null);
        if (null != bingPic) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadingPicImg();
        }
        String weatherStr = sp.getString("weather", null);
        final String weatherId;
        if (null != weatherStr) {
            //有缓存直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherStr);
            weatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            //无缓存通过服务器查询
            weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void loadingPicImg() {
        String requestPicUrl = "http://guolin.tech/api/bing_pic";
        HttpUtils.sendOkHttpRequest(requestPicUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor sp = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this)
                        .edit();
                sp.putString("bing_pic", bingPic);
                sp.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    public void requestWeather(final String weatherId) {

        String weatherUrl = Common.URL_WEATHER_CODE + "weather?city=" + weatherId + "&key=" + Common.URL_KEY;
        HttpUtils.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != weather && "ok".equalsIgnoreCase(weather.status)) {
                            SharedPreferences.Editor sp = PreferenceManager.getDefaultSharedPreferences
                                    (WeatherActivity.this).edit();
                            sp.putString("weather", responseText);
                            sp.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadingPicImg();
    }

    /**
     * 处理并显示数据
     */
    public void showWeatherInfo(Weather weather) {
        if (null != weather && "ok".equalsIgnoreCase(weather.status)) {
            String cityName = weather.basic.cityName;
            String degree = weather.now.temperature + "℃";
            String weatherInfo = weather.now.more.info;
            titleCity.setText(cityName);
            degreeText.setText(degree);
            weatherInfoText.setText(weatherInfo);
            weatherWindSc.setText(weather.now.wind.sc);
            forecastLayout.removeAllViews();
            for (Forecast forecast : weather.forecastList) {
                View view = LayoutInflater.from(this).inflate(R.layout.forcast_item, forecastLayout, false);
                TextView dataText = (TextView) view.findViewById(R.id.data_text);
                TextView infoText = (TextView) view.findViewById(R.id.info_text);
                TextView maxText = (TextView) view.findViewById(R.id.max_text);
                TextView windScText = (TextView) view.findViewById(R.id.wind_sc);
                dataText.setText(forecast.data);
                infoText.setText(forecast.more.info);
                maxText.setText(forecast.temperature.max + "℃" + "/" + forecast.temperature.min + "℃");
                windScText.setText(forecast.wind.sc);
                forecastLayout.addView(view);
            }
            if (null != weather.aqi) {
                aqiText.setText(weather.aqi.city.aqi);
                pm25Text.setText(weather.aqi.city.pm25);
                aqiZhiliang.setText(weather.aqi.city.qlty);
            }
            airBrf.setText(weather.suggestion.air.brf);
            airText.setText(weather.suggestion.air.info);
            comfortBrf.setText(weather.suggestion.comfort.brf);
            comfortText.setText(weather.suggestion.comfort.info);
            carWashBrf.setText(weather.suggestion.carWash.brf);
            carWashText.setText(weather.suggestion.carWash.info);
            sportBrf.setText(weather.suggestion.sport.brf);
            sportText.setText(weather.suggestion.sport.info);
            drsgBrf.setText(weather.suggestion.drsg.brf);
            drsgText.setText(weather.suggestion.drsg.info);
            fluBrf.setText(weather.suggestion.flu.brf);
            fluText.setText(weather.suggestion.flu.info);
            travBrf.setText(weather.suggestion.trav.brf);
            travText.setText(weather.suggestion.trav.info);
            uvBrf.setText(weather.suggestion.uv.brf);
            uvText.setText(weather.suggestion.uv.info);
            mPointValues.clear();
            mAxisXValues.clear();
            getAxisXLables(weather.hourly);//获取x轴的标注
            getAxisPoints(weather.hourly);//获取坐标点
            initLineChart();//初始化
            Intent intent = new Intent(this, AutoUpdataService.class);
            startService(intent);
        } else {
            Toast.makeText(this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape
        // .SQUARE）
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(2);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        //		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示	
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示	
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴  
        Axis axisX = new Axis(); //X轴  
        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示 
        //        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色

        //        	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(11);//设置字体大小
        axisX.setMaxLabelChars(5); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部     
        //        data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线


        Axis axisY = new Axis();  //Y轴  
        axisY.setName("");//y轴标注
        axisY.setTextSize(11);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移  
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        lineChart.setMaxZoom((float) 3);//缩放比例
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }

    /**
     * X 轴的显示
     */
    private void getAxisXLables(List<Hourly> hourly) {
        for (int i = 0; i < hourly.size(); i++) {
            String[] strings = hourly.get(i).data.split(" ");
            mAxisXValues.add(new AxisValue(i).setLabel(strings[1]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(List<Hourly> hourly) {
        for (int i = 0; i < hourly.size(); i++) {
            mPointValues.add(new PointValue(i, Float.valueOf(hourly.get(i).tmp)));
        }
    }
}
//jhfghfh