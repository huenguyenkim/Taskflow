package com.taskflow.service.impl;

import com.taskflow.dto.response.UserResponse;
import com.taskflow.entity.Role;
import com.taskflow.entity.User;
import com.taskflow.exception.ForbiddenException;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.UserMapper;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponse getCurrentUser() {

        User user = getAuthenticatedUser();

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User currentUser = getAuthenticatedUser();

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                id
                        )
                );

        if (!currentUser.getRole().equals(Role.ADMIN)
                && !currentUser.getId().equals(id)) {

            throw new ForbiddenException(
                    "You do not have permission to access this user"
            );
        }

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        User currentUser = getAuthenticatedUser();

        if (!currentUser.getRole().equals(Role.ADMIN)) {

            throw new ForbiddenException(
                    "Only admin can view all users"
            );
        }

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {

        User currentUser = getAuthenticatedUser();

        if (!currentUser.getRole().equals(Role.ADMIN)) {

            throw new ForbiddenException(
                    "Only admin can delete users"
            );
        }

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                id
                        )
                );

        userRepository.delete(user);
    }

    /**
     * Lấy user hiện tại từ JWT token
     */
    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );
    }
}