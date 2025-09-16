package com.ieum.study.domain;

import com.ieum.user.domain.BaseTimeEntity;
import com.ieum.user.domain.User; // User 엔티티의 실제 경로에 맞게 수정해야 합니다.
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 한 명의 유저가 같은 스터디에 중복으로 참여하는 것을 방지하기 위해 복합 유니크 키 설정
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
    private StudyRole role; // 멤버의 역할 (LEADER, MEMBER)

    @Builder
    public Membership(User user, Study study, StudyRole role) {
        this.user = user;
        this.study = study;
        this.role = role;
    }
}