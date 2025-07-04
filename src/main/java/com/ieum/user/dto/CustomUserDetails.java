package com.ieum.user.dto;

import com.ieum.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * [추가 제안]
     * User 엔티티의 ID(PK)를 반환하는 메서드.
     * 컨트롤러에서 @AuthenticationPrincipal을 통해 주입받은 UserDetails 객체로부터
     * 손쉽게 사용자의 ID를 얻기 위해 사용됩니다.
     */
    public Long getUserId() {
        return this.user.getId();
    }

    /**
     * 사용자의 권한 목록을 반환합니다.
     * 현재는 모든 사용자에게 'ROLE_USER' 권한을 부여합니다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // User 엔티티에 Role 필드를 추가하여 동적으로 권한을 부여하는 방식으로 확장할 수 있습니다.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * 사용자의 비밀번호를 반환합니다.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 사용자의 고유 식별자(여기서는 이메일)를 반환합니다.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * 계정이 만료되지 않았는지 여부를 반환합니다. (true: 만료 안됨)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠기지 않았는지 여부를 반환합니다. (true: 잠기지 않음)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명(비밀번호)이 만료되지 않았는지 여부를 반환합니다. (true: 만료 안됨)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활성화 상태인지 여부를 반환합니다. (true: 활성화)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}