package com.ieum.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "내 프로필 정보 수정 요청 DTO")
public class ProfileUpdateRequest {

    @Schema(description = "수정할 이름", example = "김이음")
    private String name;

    @Schema(description = "수정할 자기소개", example = "안녕하세요. '이음'입니다.")
    private String introduction;
}