package movie.review_site.security;

import lombok.RequiredArgsConstructor;
import movie.review_site.entity.User;
import movie.review_site.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 데이터베이스에서 사용자 검색
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        // 사용자 권한 생성 (Spring Security에서 사용하는 GrantedAuthority로 변환)
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // Spring Security의 UserDetails 객체 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getPassword()) // 암호화된 비밀번호여야 합니다.
                .authorities(Collections.singleton(authority)) // 사용자 권한 설정
                .build();
    }
}
