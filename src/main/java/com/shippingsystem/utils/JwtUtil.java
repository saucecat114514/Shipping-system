package com.shippingsystem.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成访问令牌
     */
    public String generateAccessToken(String username, Long userId, String roleCode) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("roleCode", roleCode);
        return generateToken(claims, username, expiration);
    }

    /**
     * 生成刷新令牌
     */
    public String generateRefreshToken(String username) {
        return generateToken(new HashMap<>(), username, refreshExpiration);
    }

    /**
     * 生成令牌
     */
    private String generateToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 从令牌中获取用户名
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.error("从JWT令牌中获取用户名失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从令牌中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Object userId = claims.get("userId");
            if (userId instanceof Number) {
                return ((Number) userId).longValue();
            }
            return null;
        } catch (Exception e) {
            log.error("从JWT令牌中获取用户ID失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从令牌中获取角色编码
     */
    public String getRoleCodeFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return (String) claims.get("roleCode");
        } catch (Exception e) {
            log.error("从JWT令牌中获取角色编码失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取令牌过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getExpiration();
        } catch (Exception e) {
            log.error("从JWT令牌中获取过期时间失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 验证令牌
     */
    public boolean validateToken(String token, String username) {
        try {
            String tokenUsername = getUsernameFromToken(token);
            return username.equals(tokenUsername) && !isTokenExpired(token);
        } catch (Exception e) {
            log.error("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 判断令牌是否过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration != null && expiration.before(new Date());
        } catch (Exception e) {
            log.error("检查JWT令牌是否过期失败: {}", e.getMessage());
            return true;
        }
    }

    /**
     * 刷新令牌
     */
    public String refreshToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            String username = claims.getSubject();
            Long userId = getUserIdFromToken(token);
            String roleCode = getRoleCodeFromToken(token);
            return generateAccessToken(username, userId, roleCode);
        } catch (Exception e) {
            log.error("刷新JWT令牌失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从令牌中获取Claims
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取令牌剩余有效时间（毫秒）
     */
    public long getTokenRemainingTime(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            if (expiration != null) {
                return expiration.getTime() - System.currentTimeMillis();
            }
            return 0;
        } catch (Exception e) {
            log.error("获取JWT令牌剩余时间失败: {}", e.getMessage());
            return 0;
        }
    }
} 