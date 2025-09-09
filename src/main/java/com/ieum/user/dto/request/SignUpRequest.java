package com.ieum.user.dto.request;

import com.ieum.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignUpRequest {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Schema(description = "사용자 이메일", example = "member@ieum.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Schema(description = "비밀번호 (8자 이상)", example = "password123!")
    private String password;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Schema(description = "사용자 이름", example = "김이음")
    private String name;

    @NotBlank(message = "학번은 필수 입력 항목입니다.")
    @Schema(description = "사용자 학번", example = "202512345")
    private String studentId;

    @Size(max = 100, message = "자기소개는 100자 이내로 입력해주세요.")
    @Schema(description = "자기소개 (100자 이내)", example = "안녕하세요. '이음'입니다.")
    private String introduction;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .studentId(this.studentId)
                .introduction(this.introduction)
                .build();
    }
}