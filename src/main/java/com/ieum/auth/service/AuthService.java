package com.ieum.auth.service;

import com.ieum.auth.dto.request.EmailLoginRequest;
import com.ieum.auth.dto.request.ReissueRequest;
import com.ieum.auth.dto.request.StudentIdLoginRequest;
import com.ieum.auth.dto.response.TokenResponse;
import com.ieum.common.exception.BusinessException;
import com.ieum.common.exception.ErrorCode;
import com.ieum.common.jwt.JwtUtil;
import com.ieum.user.domain.User;
import com.ieum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenResponse loginByEmail(EmailLoginRequest request) {
        // 1. request의 email, password를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. 실제 검증 (비밀번호 일치 확인)
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 생성
        String accessToken = jwtUtil.generateAccessToken(authentication.getName());
        String refreshToken = jwtUtil.generateRefreshToken(authentication.getName());

        return TokenResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse loginByStudentId(StudentIdLoginRequest request) {
        // 1. 학번으로 사용자 조회
        User user = userRepository.findByStudentId(request.getStudentId())
                .orElseThrow(() -> new UsernameNotFoundException("해당 학번의 사용자를 찾을 수 없습니다: " + request.getStudentId()));

        // 2. 조회된 사용자의 '이메일'과 요청으로 받은 '비밀번호'로 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword());

        // 3. 실제 검증 (위 loginByEmail과 동일한 로직)
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 4. 인증 정보를 기반으로 JWT 생성
        String accessToken = jwtUtil.generateAccessToken(authentication.getName());
        String refreshToken = jwtUtil.generateRefreshToken(authentication.getName());

        return TokenResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse reissue(ReissueRequest request) {
        // 1. Refresh Token 검증
        if (!jwtUtil.validateToken(request.getRefreshToken())) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token에서 사용자 이메일 가져오기
        // (주의: 이 단계에서는 Access Token이 만료되었어도, 페이로드의 정보는 유효해야 함)
        String email = jwtUtil.getEmailFromToken(request.getRefreshToken());

        // 3. 새로운 Access Token 생성
        String newAccessToken = jwtUtil.generateAccessToken(email);

        // 4. 새로운 토큰을 DTO에 담아 반환
        return TokenResponse.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken()) // 기존 리프레시 토큰 그대로 반환
                .build();
    }

}