package com.ieum.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Phase 0 정의에 따른 공통 에러 코드
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID_INPUT_VALUE", "입력값이 올바르지 않습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_FAILED", "인증에 실패했습니다."),
    AUTHORIZATION_DENIED(HttpStatus.FORBIDDEN, "AUTHORIZATION_DENIED", "접근 권한이 없습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", "요청한 리소스를 찾을 수 없습니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_ERROR", "서버 내부 오류가 발생했습니다."),

    // 회원가입 기능에 특화된 에러 코드 (409 Conflict)
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "DUPLICATE_RESOURCE", "이미 사용 중인 이메일입니다."),
    STUDENT_ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "DUPLICATE_RESOURCE", "이미 사용 중인 학번입니다.");


    private final HttpStatus status;
    private final String code; // 명세에 정의된 "ERROR_CODE_STRING"
    private final String message;
}