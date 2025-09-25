package com.ieum.study.domain;

import com.ieum.user.domain.BaseTimeEntity;
import com.ieum.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 중복 참여 방지
@Table(name = "membership", uniqueConstraints = {
        @UniqueConstraint(
                name = "membership_uk",
                columnNames = {"user_id", "study_id"}
        )
})

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyRole role; // LEADER, MEMBER (ADMINISTRATOR 추가 구현)

    @Builder
    public Membership(User user, Study study, StudyRole role) {
        this.user = user;
        this.study = study;
        this.role = role;
    }
}