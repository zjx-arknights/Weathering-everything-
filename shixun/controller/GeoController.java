package com.shixun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shixun.pojo.City;
import com.shixun.pojo.LatitudeAndLongitudepojo;
import com.shixun.pojo.Query;
import com.shixun.pojo.Result;
import com.shixun.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController

public class GeoController {

    @Autowired
    private GeoService geoService;

    public String mylocationId;

    //利用城市名称搜索
    @PostMapping("/api/locations")
    public Result getCityInfo(@RequestBody Query query) {
        Result  result = geoService.getCityInfo(query.getQuery());
        return  result;
    }

    @PostMapping("/api/position")
    //利用城市经纬度搜索
    public Result getCityInfoByLatitudeAndlongitude(@RequestBody LatitudeAndLongitudepojo query) {
        Result  result = geoService.getCityInfo(query.getPosition().getLng()+","+query.getPosition().getLat());
        return  result;
    }

    //实时天气 for API | 和风天气开发服务
    @PostMapping("/api/dailyweather")
    public Result getDaylyWeather(@RequestBody City city) {
        Result  result = geoService.getDailyWeather(city.getLocationId());
        return  result;
    }

    //每日天气预报 for API | 和风天气开发服务
    @PostMapping("/api/everydayweather")
    public Result everyDaylyWeather(@RequestBody City city) {
        Result  result = geoService.geteveryDayWeather(city.getLocationId());
        return  result;
    }
    //逐小时天气预报 for API | 和风天气开发服务
    @PostMapping("/api/hourweather")
    public Result hourlyWeather(@RequestBody City city) {
        Result  result = geoService.gethourWeather(city.getLocationId());
        return  result;
    }

    //天气指数预报 for API | 和风天气开发服务
    @PostMapping("/api/lifeweather")
    public Result lifeWeather(@RequestBody City city) {
        Result  result = geoService.getlifeWeather(city.getLocationId());
        return  result;
    }

    //天气灾害预警 for API | 和风天气开发服务
    @PostMapping("/api/warning")
    public Result weatherWarning(@RequestBody City city) {
        Result  result = geoService.getweatherwarning(city.getLocationId());
        return  result;
    }



}
