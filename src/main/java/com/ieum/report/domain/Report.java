package com.ieum.report.domain;

import com.ieum.user.domain.BaseTimeEntity;
import com.ieum.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식별 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter; // 신고자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser; // 피신고자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason; // 신고 사유

    @Column(columnDefinition = "TEXT")
    private String details; // 상세 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status; // 처리 상태

    @Builder
    public Report(User reporter, User reportedUser, ReportReason reason, String details, ReportStatus status) {
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.reason = reason;
        this.details = details;
        this.status = status;
    }
}