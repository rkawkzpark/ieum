package com.ieum.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String studentId;

    @Column(length = 100)
    private String introduction;


    @Builder
    public User(String email, String password, String name, String studentId, String introduction) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.studentId = studentId;
        this.introduction = introduction;
    }

    /**
     * 사용자의 이름과 자기소개를 수정합니다.
     * @param name 새로운 이름
     * @param introduction 새로운 자기소개
     */
    public void updateProfile(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }
}