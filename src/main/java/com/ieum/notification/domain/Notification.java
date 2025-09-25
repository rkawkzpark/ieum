package com.ieum.notification.domain;

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
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 알림 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver; // 알림을 받는 사용자

    @Column(nullable = false)
    private String content; // 알림 내용

    @Column(nullable = false)
    private boolean isRead = false; // 읽음 여부, 기본값 false

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type; // 알림 종류

    @Column(nullable = false)
    private String relatedUrl; // 관련 페이지 URL

    @Builder
    public Notification(User receiver, String content, NotificationType type, String relatedUrl) {
        this.receiver = receiver;
        this.content = content;
        this.type = type;
        this.relatedUrl = relatedUrl;
    }

    // 읽음 처리 편의 메소드
    public void markAsRead() {
        this.isRead = true;
    }
}