package com.ieum.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "인증 토큰 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    @Schema(description = "인증 타입", example = "Bearer")
    private String grantType;

    @Schema(description = "액세스 토큰 (실제 인증 토큰)")
    private String accessToken;

    @Schema(description = "리프레시 토큰 (액세스 토큰 재발급용)")
    private String refreshToken;
}