package com.ieum.user.service;

import com.ieum.common.exception.BusinessException;
import com.ieum.common.exception.ErrorCode;
import com.ieum.user.domain.User;
import com.ieum.user.dto.request.SignUpRequest;
import com.ieum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {

        // 중복 email 검증
        if (userRepository.existsByEmail(request.getEmail())) {
            // 수정된 ErrorCode를 사용합니다.
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 중복 학번 검증
        if (userRepository.existsByStudentId(request.getStudentId())) {
            // 수정된 ErrorCode를 사용합니다.
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }

        User user = request.toEntity(passwordEncoder);
        userRepository.save(user);
    }
}