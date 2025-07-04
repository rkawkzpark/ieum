package com.ieum.user.controller;

import com.ieum.common.dto.ApiResponse;
import com.ieum.common.dto.ErrorResponse;
import com.ieum.user.dto.CustomUserDetails;
import com.ieum.user.dto.request.ProfileUpdateRequest;
import com.ieum.user.dto.request.SignUpRequest;
import com.ieum.user.dto.response.ProfileResponse;
import com.ieum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "사용자가 이메일, 비밀번호, 이름, 학번을 입력하여 회원가입을 요청합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "유효하지 않은 입력 값", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 존재하는 리소스 (이메일 또는 학번 중복)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PostMapping("/sign-up")
    public ApiResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        userService.signUp(request);
        return ApiResponse.success(null);
    }
    // 1. 내 정보 조회 API 추가
    @Operation(summary = "내 정보 조회", description = "현재 로그인된 사용자의 프로필 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "내 정보 조회 성공", content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "bearerAuth") // 이 API는 인증이 필요함을 Swagger에 명시
    @GetMapping("/me")
    public ApiResponse<ProfileResponse> getMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        ProfileResponse profile = userService.getUserProfile(userId);
        return ApiResponse.success(profile);
    }

    // 2. 내 정보 수정 API 추가
    @Operation(summary = "내 정보 수정", description = "현재 로그인된 사용자의 프로필 정보(이름, 자기소개)를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "내 정보 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "bearerAuth") // 이 API는 인증이 필요함을 Swagger에 명시
    @PatchMapping("/me")
    public ApiResponse<Void> updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ProfileUpdateRequest request
    ) {
        Long userId = userDetails.getUserId();
        userService.updateUserProfile(userId, request);
        return ApiResponse.success(null);
    }
}