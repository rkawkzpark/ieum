package com.ieum.study.dto.request;

import com.ieum.study.domain.StudyType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 스터디 생성을 위한 요청 데이터를 담는 DTO
 */
@Getter
public class StudyCreateRequestDto {

    @NotBlank(message = "스터디 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "스터디 설명은 필수입니다.")
    private String description;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    @NotNull(message = "스터디 진행 방식은 필수입니다.")
    private StudyType type;

    private String region; // 오프라인 스터디의 경우

    @NotNull(message = "시작일은 필수입니다.")
    @FutureOrPresent(message = "시작일은 오늘 또는 그 후로 설정 가능합니다.")
    private LocalDate startDate;

    @NotNull(message = "종료일은 필수입니다.")
    private LocalDate endDate;

    @NotNull(message = "최대 정원은 필수입니다.")
    @Min(value = 2, message = "최대 정원은 2명 이상이어야 합니다.")
    private Integer maxMembers;
}