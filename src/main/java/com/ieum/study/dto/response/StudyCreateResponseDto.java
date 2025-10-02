package com.ieum.study.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 스터디 생성 후 생성된 스터디의 ID를 반환하는 DTO
 */
@Getter
@AllArgsConstructor
public class StudyCreateResponseDto {
    private Long studyId;
}