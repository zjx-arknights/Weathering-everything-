package com.shixun.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private String name;
    private String country;
    private String locationId;
    private double latitude;
    private double longitude;


}
