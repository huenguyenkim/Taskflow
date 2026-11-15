package com.taskflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    /**
     * Khóa bí mật phải dài tối thiểu 256 bit
     */
    private static final String SECRET_KEY =
            "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWYxMjM0NTY=";

    /**
     * 24 giờ
     */
    private static final long JWT_EXPIRATION =
            1000 * 60 * 60 * 24;

    @Override
    public String generateToken(String email) {

        return generateToken(
                Map.of(),
                email
        );
    }

    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            String email
    ) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + JWT_EXPIRATION
                        )
                )
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    @Override
    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    @Override
    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isTokenValid(
            String token,
            String email
    ) {

        String username =
                extractUsername(token);

        return username.equals(email)
                && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(
            String token
    ) {

        return extractExpiration(token)
                .before(new Date());
    }

    /**
     * Đọc dữ liệu bất kỳ từ JWT
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Lấy thời gian hết hạn
     */
    private Date extractExpiration(
            String token
    ) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    /**
     * Chuyển SECRET_KEY thành Key
     */
    private Key getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(
                        SECRET_KEY
                );

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }
}