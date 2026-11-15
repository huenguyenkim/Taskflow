package com.taskflow.security;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {

    /**
     * Tạo JWT token từ email
     */
    String generateToken(String email);

    /**
     * Tạo JWT token với thông tin mở rộng
     */
    String generateToken(
            Map<String, Object> extraClaims,
            String email
    );

    /**
     * Lấy email từ token
     */
    String extractUsername(String token);

    /**
     * Lấy toàn bộ claims
     */
    Claims extractAllClaims(String token);

    /**
     * Kiểm tra token hợp lệ
     */
    boolean isTokenValid(
            String token,
            String email
    );

    /**
     * Kiểm tra token hết hạn chưa
     */
    boolean isTokenExpired(String token);
}
