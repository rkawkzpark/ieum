package com.ieum.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "이메일 로그인 요청 DTO")
@Getter
@NoArgsConstructor
public class EmailLoginRequest {

    @Schema(description = "사용자 이메일", example = "test@university.ac.kr")
    private String email;

    @Schema(description = "비밀번호", example = "password123!")
    private String password;
}