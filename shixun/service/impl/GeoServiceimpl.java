package com.shixun.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.shixun.config.CurrentUser;
import com.shixun.mapper.UserMapper;
import com.shixun.pojo.Result;
import com.shixun.service.GeoService;
import com.shixun.service.JwtService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.zip.GZIPInputStream;

@Service
public class GeoServiceimpl implements GeoService {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private CurrentUser currentUser;
    JwtService  jwtService = new JwtService();
    private  final String jwt_token = jwtService.generateJwt();

    // 新增属性
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final String apiUrlBase = "https://kh5ctu8478.re.qweatherapi.com";

    // 构造函数初始化属性
    public GeoServiceimpl() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Result cityreaserch(String cityname) {
        return null;
    }

    @Override
    public Result getDailyWeather(String locationId) {
        String apiUrl = apiUrlBase +"/v7/weather/now?location=" +locationId;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + jwt_token)
                .header("Accept-Encoding", "gzip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Empty response body");


            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
            usermapper.updateuserlocationid(currentUser.getCurrentUser(), locationId);
            return Result.success(objectMapper.readValue(gzipStream, Object.class));


        } catch (IOException e) {
            return Result.error("Failed to fetch city info: " + e.getMessage());
        }
    }

    @Override
    public Result geteveryDayWeather(String locationId) {
        String apiUrl = apiUrlBase +"/v7/weather/15d?location=" +locationId;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + jwt_token)
                .header("Accept-Encoding", "gzip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Empty response body");


            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
            return Result.success(objectMapper.readValue(gzipStream, Object.class));


        } catch (IOException e) {
            return Result.error("Failed to fetch city info: " + e.getMessage());
        }
    }

    @Override
    public Result gethourWeather(String locationId) {
        String apiUrl = apiUrlBase +"/v7/weather/24h?location=" +locationId;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + jwt_token)
                .header("Accept-Encoding", "gzip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Empty response body");


            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
            return Result.success(objectMapper.readValue(gzipStream, Object.class));


        } catch (IOException e) {
            return Result.error("Failed to fetch city info: " + e.getMessage());
        }
    }

    @Override
    public Result getlifeWeather(String locationId) {
        String apiUrl = apiUrlBase +"/v7/indices/1d?type=0&location=" +locationId;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + jwt_token)
                .header("Accept-Encoding", "gzip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Empty response body");


            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
            return Result.success(objectMapper.readValue(gzipStream, Object.class));


        } catch (IOException e) {
            return Result.error("Failed to fetch city info: " + e.getMessage());
        }
    }

    // 获取天气预警信息
    @Override
    public Result getweatherwarning(String locationId) {
        String apiUrl = apiUrlBase +"/v7/warning/now?location=" +locationId;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + jwt_token)
                .header("Accept-Encoding", "gzip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);//检查响应是否成功

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Empty response body");//检查响应体是否为空


            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
            return Result.success(objectMapper.readValue(gzipStream, Object.class));


        } catch (IOException e) {
            return Result.error("Failed to fetch city info: " + e.getMessage());
        }
    }

    //  获取城市信息
    @Override
    public Result getCityInfo(String cityName) {
        String apiUrl = apiUrlBase +"/geo/v2/city/lookup?location=" +cityName;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + jwt_token)
                .header("Accept-Encoding", "gzip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);//检查响应是否成功

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Empty response body");//检查响应体是否为空


            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
            return Result.success(objectMapper.readValue(gzipStream, Object.class));


        } catch (IOException e) {
            return Result.error("Failed to fetch city info: " + e.getMessage());
        }
    }

    @Override
    public String getlocationId(String cityName) {
        // 使用类属性
        String apiUrl = apiUrlBase +"/geo/v2/city/lookup?location=" +cityName;
        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + jwt_token)
                .header("Accept-Encoding", "gzip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Empty response body");

            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
            JsonNode rootNode = objectMapper.readTree(gzipStream);
            JsonNode locationArray = rootNode.get("location");

            if (locationArray != null && locationArray.isArray() && locationArray.size() > 0) {
                return locationArray.get(0).get("id").asText(); // 返回第一个id值
            }

            return null; // 如果没有location数据则返回null

        } catch (IOException e) {
            return null;
        }
    }
}
