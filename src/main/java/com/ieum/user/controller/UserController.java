package com.ieum.user.controller;

import com.ieum.common.dto.ApiResponse;
import com.ieum.common.dto.ErrorResponse;
import com.ieum.user.dto.request.SignUpRequest;
import com.ieum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}