package com.shixun.utils;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 提供生成和解析 JWT 令牌的功能
 */
public class JwtUtils {

    // 签名密钥，与测试类中保持一致
    private static final String SECRET_KEY = "c2VjcmV0Cg==";

    // 过期时间设置为12小时（单位：毫秒）
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 12;

    /**
     * 生成 JWT 令牌
     * @param claims 要包含在令牌中的声明信息
     * @return 生成的 JWT 令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析 JWT 令牌
     * @param token 要解析的 JWT 令牌字符串
     * @return 包含声明信息的 Claims 对象
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 快速生成包含id和username的令牌（可选方法）
     * @param id 用户ID
     * @param username 用户名
     * @return 生成的 JWT 令牌字符串
     */
    public static String generateSimpleToken(Integer id, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        return generateToken(claims);
    }
}