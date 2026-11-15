package com.taskflow.controller;

import com.taskflow.dto.response.UserResponse;
import com.taskflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Lấy thông tin user hiện tại
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {

        UserResponse response =
                userService.getCurrentUser();

        return ResponseEntity.ok(response);
    }

    /**
     * Lấy user theo id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {

        UserResponse response =
                userService.getUserById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Lấy danh sách tất cả user
     * Chỉ ADMIN được truy cập
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>>
    getAllUsers() {

        List<UserResponse> users =
                userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    /**
     * Xóa user
     * Chỉ ADMIN được phép
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}