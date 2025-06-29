package com.ieum.common.jwt;

import com.ieum.user.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * 실제 필터링 로직을 수행하는 곳입니다.
     * JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext에 저장하는 역할을 수행합니다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Request Header에서 토큰을 추출합니다.
        String jwt = resolveToken(request);

        // 2. 토큰 유효성 검사를 수행합니다.
        if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
            // 토큰이 유효할 경우, 토큰에서 사용자 정보를 가져옵니다.
            String email = jwtUtil.getEmailFromToken(jwt);

            // 사용자 정보를 기반으로 UserDetails 객체를 생성합니다.
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // UserDetails를 기반으로 Authentication 객체를 생성합니다.
            // 비밀번호는 이미 검증되었으므로 null을 전달합니다.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // SecurityContext에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), request.getRequestURI());
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", request.getRequestURI());
        }

        // 다음 필터로 제어를 넘깁니다.
        filterChain.doFilter(request, response);
    }

    /**
     * Request Header에서 'Bearer ' 접두사를 제거하고 토큰 값만 추출합니다.
     * @param request HttpServletRequest 객체
     * @return 추출된 토큰 문자열
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}