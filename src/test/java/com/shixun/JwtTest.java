package com.shixun;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtTest {

    @Test
    public void testGenerateJwt() {
        Map<String, Object> datamap = new HashMap<>();
        datamap.put("id",1 );
        datamap.put("username", "admin");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "secret")
                .addClaims(datamap)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
                .compact();

    }

    @Test
    public void testParseJwt() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTYxNjE5NjE5Mn0.5ZYwxq5X5x5X5X5X5X5X}";
        Claims claims =Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(jwt)
                .getBody();
    }
}
