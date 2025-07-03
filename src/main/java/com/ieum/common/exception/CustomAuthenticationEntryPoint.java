package com.ieum.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ieum.common.dto.ApiResponse; // ApiResponse import 추가
import com.ieum.common.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ErrorCode errorCode = ErrorCode.AUTHENTICATION_FAILED;

        // 1. ErrorResponse 객체 생성
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        // 2. ApiResponse로 한번 더 감싸서 최종 응답 형태를 통일
        ApiResponse<?> apiResponse = ApiResponse.error(errorResponse);

        // 응답 상태 및 헤더 설정
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 3. ApiResponse 객체를 JSON으로 변환하여 응답
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }
}