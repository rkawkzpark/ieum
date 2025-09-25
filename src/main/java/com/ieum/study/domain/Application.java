package com.ieum.study.domain;

import com.ieum.user.domain.BaseTimeEntity;
import com.ieum.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 중복 지원 방지
@Table(name = "application", uniqueConstraints = {
        @UniqueConstraint(
                name = "application_uk",
                columnNames = {"applicant_id", "study_id"}
        )
})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant; // 지원자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study; // 지원한 스터디

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message; // 지원 메시지

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status; // 스터디 상태

    @Builder
    public Application(User applicant, Study study, String message, ApplicationStatus status) {
        this.applicant = applicant;
        this.study = study;
        this.message = message;
        this.status = status;
    }
}