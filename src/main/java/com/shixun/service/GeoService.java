package com.shixun.service;

import com.shixun.pojo.Result;
import org.springframework.stereotype.Service;


public interface GeoService {
    Result cityreaserch(String cityname);

    Result getCityInfo(String query);

    String getlocationId(String cityName);

    Result getDailyWeather(String locationId);

    Result geteveryDayWeather(String locationId);

    Result gethourWeather(String locationId);

    Result getlifeWeather(String locationId);

    Result getweatherwarning(String locationId);
}
