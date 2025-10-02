package com.ieum.study.controller;

import com.ieum.study.dto.response.StudyCreateResponseDto;
import com.ieum.study.dto.request.StudyCreateRequestDto;
import com.ieum.study.service.StudyService;
import com.ieum.user.domain.User;
import com.ieum.user.dto.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studies")
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<StudyCreateResponseDto> createStudy(
            @Valid @RequestBody StudyCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();

        Long studyId = studyService.createStudy(requestDto, user);
        StudyCreateResponseDto response = new StudyCreateResponseDto(studyId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}