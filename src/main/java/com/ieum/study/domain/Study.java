package com.ieum.study.domain;

import com.ieum.user.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식별 번호

    @Column(nullable = false, length = 100)
    private String title; // 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; // 설명

    @Column(nullable = false, length = 50)
    private String category; // 주제

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyType type; // 진행 방식

    private String region; // 진행 지역

    @Column(nullable = false)
    private LocalDate startDate; // 시작일

    @Column(nullable = false)
    private LocalDate endDate; // 종료일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyStatus status; //  상태

    @Column(nullable = false)
    private Integer maxMembers; // 최대 정원

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> members = new ArrayList<>();

    @Builder
    public Study(String title, String description, String category, StudyType type, String region, LocalDate startDate, LocalDate endDate, StudyStatus status, Integer maxMembers) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.region = region;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.maxMembers = maxMembers;
    }
}