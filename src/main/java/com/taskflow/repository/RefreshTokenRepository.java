package com.taskflow.repository;

import com.taskflow.entity.RefreshToken;
import com.taskflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    /**
     * Tìm refresh token theo token
     */
    Optional<RefreshToken> findByToken(
            String token
    );

    /**
     * Tìm refresh token theo user
     */
    Optional<RefreshToken> findByUser(
            User user
    );

    /**
     * Xóa refresh token của user
     */
    void deleteByUser(
            User user
    );
}