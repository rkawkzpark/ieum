package com.ieum.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "학번 로그인 요청 DTO")
@Getter
@NoArgsConstructor
public class StudentIdLoginRequest {

    @Schema(description = "학번", example = "202012345")
    private String studentId;

    @Schema(description = "비밀번호", example = "password123!")
    private String password;
}