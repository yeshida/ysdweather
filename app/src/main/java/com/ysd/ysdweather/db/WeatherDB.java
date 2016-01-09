package com.ysd.ysdweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ysd.ysdweather.model.City;
import com.ysd.ysdweather.model.County;
import com.ysd.ysdweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public class WeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME ="ysd_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION=1;

    private static WeatherDB weatherDB;
    private SQLiteDatabase db;
    /**
     * 将构造方法私有化
     */
    private WeatherDB(Context context) {
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 获取weatherDB的实例
     */
    public synchronized static WeatherDB getInstance(Context context){
        if (weatherDB==null) {
            weatherDB = new WeatherDB(context);
        }
        return  weatherDB;
    }

    /**
     * 将province实例存储到数据库
     */
   public void saveProvince(Province province) {
        if (province!=null) {
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }
    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvinces(){
        List<Province> list= new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());

        }
        return  list;
    }
    /**
     * 将city实例存储到数据
     */
    public void saveCity(City city){
        if (city!=null) {
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City", null, values);
        }
    }
    /**
     * 从数据库读取某省下所有的城市信息
     */
    public List<City> loadCitys(int provinceId){
        List<City> list= new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        //Cursor cursor = db.query("City", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                list.add(city);
            }while (cursor.moveToNext());

        }
        return  list;
    }
    /**
     * 将County实例存储到数据
     */
    public void saveCounty(County county){
        if (county!=null) {
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * 从数据库读取某城市下所有的县信息
     */
    public List<County> loadCountys(int cityId){
        List<County> list= new ArrayList<County>();
        //县的信息无法展示，是这里的查询有问题，new String[]{String.valueOf(cityId)}直接写成null了，但是这里方法没报错  可能是识别成city_id=null 故不报错。
        Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        //Cursor cursor = db.query("County", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                list.add(county);
            }while (cursor.moveToNext());

        }
        return  list;
    }
    public  void deleteProvince() {
        db.delete("Province", null, null);
        Log.i("删除省","");
    }
    public  void deleteCity() {
        db.delete("City", null, null);
        Log.i("删除市", "");
    }
    public  void deleteCountys() {
        db.delete("County", null, null);
        Log.i("删除县", "");
    }
}
