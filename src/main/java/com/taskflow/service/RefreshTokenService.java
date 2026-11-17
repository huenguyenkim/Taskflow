package com.taskflow.service;

import com.taskflow.entity.RefreshToken;
import com.taskflow.entity.User;

public interface RefreshTokenService {

    /**
     * Tạo refresh token mới cho user
     */
    RefreshToken createRefreshToken(
            User user
    );

    /**
     * Kiểm tra refresh token hợp lệ
     */
    RefreshToken verifyRefreshToken(
            String token
    );

    /**
     * Xóa refresh token của user
     */
    void deleteByUser(
            User user
    );
}