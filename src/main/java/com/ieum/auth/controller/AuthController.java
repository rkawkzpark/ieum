package com.ieum.auth.controller;

import com.ieum.auth.dto.request.EmailLoginRequest;
import com.ieum.auth.dto.request.ReissueRequest;
import com.ieum.auth.dto.request.StudentIdLoginRequest;
import com.ieum.auth.dto.response.TokenResponse;
import com.ieum.auth.service.AuthService;
import com.ieum.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "사용자 인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "이메일 로그인")
    @PostMapping("/login/email")
    public ResponseEntity<ApiResponse<TokenResponse>> loginByEmail(@RequestBody EmailLoginRequest request) {
        TokenResponse tokenResponse = authService.loginByEmail(request);
        return ResponseEntity.ok(ApiResponse.success(tokenResponse));
    }

    @Operation(summary = "학번 로그인")
    @PostMapping("/login/student-id")
    public ResponseEntity<ApiResponse<TokenResponse>> loginByStudentId(@RequestBody StudentIdLoginRequest request) {
        TokenResponse tokenResponse = authService.loginByStudentId(request);
        return ResponseEntity.ok(ApiResponse.success(tokenResponse));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@RequestBody ReissueRequest request) {
        TokenResponse tokenResponse = authService.reissue(request);
        return ResponseEntity.ok(ApiResponse.success(tokenResponse));
    }

}