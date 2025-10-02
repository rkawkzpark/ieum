package com.ieum.study.service;

import com.ieum.study.domain.Membership;
import com.ieum.study.domain.Study;
import com.ieum.study.domain.StudyRole;
import com.ieum.study.domain.StudyStatus;
import com.ieum.study.dto.request.StudyCreateRequestDto;
import com.ieum.study.repository.MembershipRepository;
import com.ieum.study.repository.StudyRepository;
import com.ieum.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final MembershipRepository membershipRepository;

    /**
     * 새로운 스터디를 생성하고, 요청한 사용자를 스터디장으로 지정합니다.
     * @param requestDto 스터디 생성 요청 데이터
     * @param user       인증된 사용자 정보 (스터디장이 될 사용자)
     * @return 생성된 스터디의 ID
     */
    @Transactional
    public Long createStudy(StudyCreateRequestDto requestDto, User user) {
        // 1. DTO를 Study 엔티티로 변환 (상태는 RECRUITING으로 초기화)
        Study newStudy = Study.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .category(requestDto.getCategory())
                .type(requestDto.getType())
                .region(requestDto.getRegion())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .maxMembers(requestDto.getMaxMembers())
                .status(StudyStatus.RECRUITING)
                .build();

        // 2. Study 엔티티를 데이터베이스에 저장
        Study savedStudy = studyRepository.save(newStudy);

        // 3. 요청한 사용자를 LEADER로 하는 Membership 엔티티 생성
        Membership leaderMembership = Membership.builder()
                .user(user)
                .study(savedStudy)
                .role(StudyRole.LEADER)
                .build();

        // 4. Membership 엔티티를 데이터베이스에 저장
        membershipRepository.save(leaderMembership);

        // 5. 생성된 스터디의 ID 반환
        return savedStudy.getId();
    }
}