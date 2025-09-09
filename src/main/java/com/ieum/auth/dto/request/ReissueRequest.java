package com.ieum.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "토큰 재발급 요청 DTO")
@Getter
@NoArgsConstructor
public class ReissueRequest {

    @Schema(description = "기존 Access Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZW1iZXJAaWV1bS5jb20iLCJpYXQiOjE3MjU4NDU5MDIsImV4cCI6MTcyNTg0OTUwMn0.abcdefg123456")
    private String accessToken;

    @Schema(description = "기존 Refresh Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZW1iZXJAaWV1bS5jb20iLCJpYXQiOjE3MjU4NDU5MDIsImV4cCI6MTcyNTg0OTUwMn0.abcdefg123456")
    private String refreshToken;
}