package com.ieum.study.domain;

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
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식별 번호

    @Column(nullable = false, length = 255)
    private String title; // 글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer; // 글쓴이

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study; // 스터디

    @Builder
    public Post(String title, String content, User writer, Study study) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.study = study;
    }
}