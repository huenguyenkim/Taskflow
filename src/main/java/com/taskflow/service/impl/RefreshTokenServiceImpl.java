package com.taskflow.service.impl;

import com.taskflow.entity.RefreshToken;
import com.taskflow.entity.User;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.repository.RefreshTokenRepository;
import com.taskflow.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 7 ngày
     */
    private static final long REFRESH_TOKEN_DURATION =
            7 * 24 * 60 * 60;

    @Override
    public RefreshToken createRefreshToken(
            User user
    ) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByUser(user)
                        .orElse(
                                RefreshToken.builder()
                                        .user(user)
                                        .build()
                        );

        refreshToken.setToken(
                UUID.randomUUID().toString()
        );

        refreshToken.setExpiryDate(
                Instant.now()
                        .plusSeconds(
                                REFRESH_TOKEN_DURATION
                        )
        );

        return refreshTokenRepository.save(
                refreshToken
        );
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken verifyRefreshToken(
            String token
    ) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Refresh token not found"
                                        )
                        );

        if (refreshToken.getExpiryDate()
                .isBefore(Instant.now())) {

            throw new RuntimeException(
                    "Refresh token expired"
            );
        }

        return refreshToken;
    }

    @Override
    public void deleteByUser(
            User user
    ) {

        refreshTokenRepository
                .deleteByUser(user);
    }
}