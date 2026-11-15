package com.taskflow.service;

import com.taskflow.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    /**
     * Lấy thông tin user đang đăng nhập
     *
     * @return UserResponse
     */
    UserResponse getCurrentUser();

    /**
     * Lấy user theo id
     *
     * @param id user id
     * @return UserResponse
     */
    UserResponse getUserById(Long id);

    /**
     * Lấy toàn bộ user
     *
     * @return danh sách user
     */
    List<UserResponse> getAllUsers();

    /**
     * Xóa user
     *
     * @param id user id
     */
    void deleteUser(Long id);
}