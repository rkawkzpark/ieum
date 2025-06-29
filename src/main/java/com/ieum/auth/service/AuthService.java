package com.ieum.auth.service;

import com.ieum.auth.dto.EmailLoginRequest;
import com.ieum.auth.dto.StudentIdLoginRequest;
import com.ieum.auth.dto.TokenDto;
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
    public TokenDto loginByEmail(EmailLoginRequest request) {
        // 1. request의 email, password를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. 실제 검증 (비밀번호 일치 확인)
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 생성
        String accessToken = jwtUtil.generateAccessToken(authentication.getName());
        String refreshToken = jwtUtil.generateRefreshToken(authentication.getName());

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenDto loginByStudentId(StudentIdLoginRequest request) {
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

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}