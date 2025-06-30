package com.shixun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LatitudeAndLongitudepojo {
    private Position position;

    // 内部类定义
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private String lng;
        private String lat;
    }
}
