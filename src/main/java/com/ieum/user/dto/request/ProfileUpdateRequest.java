package com.ieum.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "내 프로필 정보 수정 요청 DTO")
public class ProfileUpdateRequest {

    @Schema(description = "새로운 사용자 이름", example = "홍길동")
    private String name;

    @Schema(description = "새로운 자기소개", example = "hello world!")
    private String introduction;
}