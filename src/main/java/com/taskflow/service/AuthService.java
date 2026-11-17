package com.taskflow.service;

import com.taskflow.dto.request.LoginRequest;
import com.taskflow.dto.request.RefreshTokenRequest;
import com.taskflow.dto.request.RegisterRequest;
import com.taskflow.dto.response.LoginResponse;
import com.taskflow.dto.response.RefreshTokenResponse;
import com.taskflow.dto.response.UserResponse;

public interface AuthService {

    /**
     * Đăng ký tài khoản
     */
    UserResponse register(
            RegisterRequest request
    );

    /**
     * Đăng nhập
     */
    LoginResponse login(
            LoginRequest request
    );

    /**
     * Làm mới Access Token
     */
    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    );
}