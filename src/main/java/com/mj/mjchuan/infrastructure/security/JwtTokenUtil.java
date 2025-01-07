package com.mj.mjchuan.infrastructure.security;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    private static final String SECRET_KEY = "your-secret-key";
    private static final long EXPIRATION_TIME = 86400000;  // 1 天

    // 生成 Token
    public static String generateToken(UserJwt userJwt) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("user",userJwt);
        return Jwts.builder()
                .setSubject(userJwt.getAccount())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // 解析 Token
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


    public static UserJwt parseJwtUser(String token) {
        Claims claims = extractClaims(token);
        Object userObj = claims.get("user");
        if (userObj instanceof HashMap<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) userObj;
            return JSONUtil.toBean(JSONUtil.toJsonStr(map), UserJwt.class);
        }
        return null;
    }

    // 检查 Token 是否过期
    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }



    // 从 JWT 中提取用户名
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 从 JWT 中提取任意 Claim（根据传入的 claimResolver）
    public <T> T extractClaim(String token, ClaimResolver<T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.resolve(claims);
    }

    // 从 Token 中提取 Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


    // 获取 Token 的过期时间
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 验证 Token 是否有效
    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Functional interface for extracting claims
    @FunctionalInterface
    public interface ClaimResolver<T> {
        T resolve(Claims claims);
    }

}