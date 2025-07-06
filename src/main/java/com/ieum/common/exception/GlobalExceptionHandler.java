package com.ieum.common.exception;

import com.ieum.common.dto.ApiResponse;
import com.ieum.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException; // import 추가
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Spring Security 인증 예외 처리 (로그인 실패 등)
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException e) {
        log.error("handleAuthenticationException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED);
        return new ResponseEntity<>(ApiResponse.error(response), HttpStatus.UNAUTHORIZED);
    }

    // DTO Validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(ApiResponse.error(response), HttpStatus.BAD_REQUEST);
    }

    // 우리만의 커스텀 예외 처리
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<?>> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(ApiResponse.error(response), errorCode.getStatus());
    }

    // 그 외 모든 예외 처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("handleException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.SERVER_ERROR);
        return new ResponseEntity<>(ApiResponse.error(response), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}