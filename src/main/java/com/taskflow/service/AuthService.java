package com.taskflow.service;

import com.taskflow.dto.request.LoginRequest;
import com.taskflow.dto.request.RegisterRequest;
import com.taskflow.dto.response.LoginResponse;
import com.taskflow.dto.response.UserResponse;

public interface AuthService {

    /**
     * Đăng ký tài khoản mới
     *
     * @param request thông tin đăng ký
     * @return thông tin người dùng sau khi đăng ký
     */
    UserResponse register(RegisterRequest request);

    /**
     * Đăng nhập hệ thống
     *
     * @param request thông tin đăng nhập
     * @return JWT Token và thông tin user
     */
    LoginResponse login(LoginRequest request);
}