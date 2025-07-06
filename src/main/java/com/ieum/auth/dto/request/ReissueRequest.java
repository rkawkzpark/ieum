package com.ieum.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "토큰 재발급 요청 DTO")
@Getter
@NoArgsConstructor
public class ReissueRequest {

    @Schema(description = "기존 Access Token")
    private String accessToken;

    @Schema(description = "기존 Refresh Token")
    private String refreshToken;
}