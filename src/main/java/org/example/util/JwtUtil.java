package org.example.util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

@Component
public class JwtUtil {


    private static final String SECRET_KEY;

    static  {
        Properties props = new Properties();
        try (InputStream input = JwtUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new FileNotFoundException("配置文件未找到！");
            }
            props.load(input);
            SECRET_KEY = props.getProperty("jwt.secret");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("加载配置失败");
        }
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                //.setExpiration(Date.from(Instant.now().plus(Duration.ofHours(2))))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(1))))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validateTokenExpiration(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration =claims.getExpiration();
            Date issuedAt =claims.getIssuedAt();
            System.out.println("expiration : "+ expiration.getTime());
            System.out.println("issuedAt : "+issuedAt.getTime());
            return ((expiration.getTime()-issuedAt.getTime())>0);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
