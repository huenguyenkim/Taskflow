package com.taskflow.mapper;

import com.taskflow.dto.request.RegisterRequest;
import com.taskflow.dto.response.UserResponse;
import com.taskflow.entity.Role;
import com.taskflow.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Convert RegisterRequest -> User Entity
     */
    public User toEntity(
            RegisterRequest request,
            PasswordEncoder passwordEncoder
    ) {

        if (request == null) {
            return null;
        }

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.USER)
                .build();
    }

    /**
     * Convert User Entity -> UserResponse
     */
    public UserResponse toResponse(
            User user
    ) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getDisplayUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    /**
     * Update User Entity
     */
    public void updateEntity(
            User user,
            String username,
            String email
    ) {

        if (user == null) {
            return;
        }

        if (username != null
                && !username.trim().isEmpty()) {

            user.setUsername(username);
        }

        if (email != null
                && !email.trim().isEmpty()) {

            user.setEmail(email);
        }
    }
}