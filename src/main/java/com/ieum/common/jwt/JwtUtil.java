package com.ieum.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private Key key;

    /**
     * 객체 초기화, secretKey를 Base64로 디코딩하여 key 변수에 할당
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Access Token 생성
     *
     * @param email 사용자의 이메일
     * @return 생성된 Access Token
     */
    public String generateAccessToken(String email) {
        return generateToken(email, accessTokenExpiration);
    }

    /**
     * Refresh Token 생성
     *
     * @param email 사용자의 이메일
     * @return 생성된 Refresh Token
     */
    public String generateRefreshToken(String email) {
        return generateToken(email, refreshTokenExpiration);
    }

    /**
     * 토큰 생성 공통 로직
     *
     * @param subject    토큰의 주체 (여기서는 이메일)
     * @param expiration 만료 시간 (밀리초)
     * @return 생성된 JWT 문자열
     */
    private String generateToken(String subject, long expiration) {
        long now = (new Date()).getTime();
        Date expirationDate = new Date(now + expiration);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰의 유효성 검증
     *
     * @param token 검증할 JWT
     * @return 토큰이 유효하면 true, 아니면 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty.", e);
        }
        return false;
    }

    /**
     * 토큰에서 사용자 이메일 추출
     *
     * @param token 파싱할 JWT
     * @return 사용자 이메일
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}