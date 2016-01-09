package com.ysd.ysdweather.util;

import android.text.TextUtils;

import com.ysd.ysdweather.db.WeatherDB;
import com.ysd.ysdweather.model.City;
import com.ysd.ysdweather.model.County;
import com.ysd.ysdweather.model.Province;


public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvinceResponse(WeatherDB weatherDB,String response){
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces!=null&&allProvinces.length>0){
                for (String p :allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据存储到Province表
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     */
    public synchronized static boolean handleCitiesResponse(WeatherDB weatherDB,String response,int provinceId){
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities!=null&&allCities.length>0){
                for (String p :allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    //将解析出来的数据存储到city表
                    weatherDB.saveCity(city);
            }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的县级数据
     */
    public synchronized static boolean handleCountiesResponse(WeatherDB weatherDB,String response,int cityId){
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties= response.split(",");
            if (allCounties!=null&&allCounties.length>0){
                for (String p :allCounties) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    //将解析出来的数据存储到county表
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
