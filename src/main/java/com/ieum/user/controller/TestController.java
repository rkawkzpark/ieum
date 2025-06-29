package com.ieum.user.controller;

import com.ieum.common.dto.ApiResponse;
import com.ieum.common.exception.BusinessException;
import com.ieum.common.exception.ErrorCode;
import com.ieum.user.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트", description = "서버 상태 및 인증 테스트 API")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Operation(summary = "서버 상태 확인 (Health Check)")
    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("이음(ieum) 프로젝트 서버가 성공적으로 실행되었습니다!"));
    }

    @Operation(summary = "인증된 사용자 정보 조회", description = "로그인된 사용자의 이메일을 반환합니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<String>> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new BusinessException(ErrorCode.AUTHENTICATION_FAILED);
        }
        return ResponseEntity.ok(ApiResponse.success("인증된 사용자 이메일: " + userDetails.getUsername()));
    }
}