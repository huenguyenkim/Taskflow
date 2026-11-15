package com.taskflow.dto.response;

import com.taskflow.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private String type;

    private Long id;

    private String username;

    private String email;

    private Role role;
}
