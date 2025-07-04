package com.ieum.user.dto.response;

import com.ieum.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "내 프로필 정보 응답 DTO")
public class ProfileResponse {

    @Schema(description = "사용자 이메일", example = "ieum@example.com")
    private String email;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    @Schema(description = "사용자 학번", example = "202500000")
    private String studentId;

    @Schema(description = "자기소개", example = "hello world!")
    private String introduction;

    @Builder
    private ProfileResponse(String email, String name, String studentId, String introduction) {
        this.email = email;
        this.name = name;
        this.studentId = studentId;
        this.introduction = introduction;
    }

    /**
     * User 엔티티를 ProfileResponse DTO로 변환하는 정적 팩토리 메서드
     * @param user User 엔티티 객체
     * @return 변환된 ProfileResponse DTO
     */
    public static ProfileResponse from(User user) {
        return ProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .studentId(user.getStudentId())
                .introduction(user.getIntroduction())
                .build();
    }
}