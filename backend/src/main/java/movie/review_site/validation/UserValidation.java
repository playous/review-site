package movie.review_site.validation;

import lombok.RequiredArgsConstructor;
import movie.review_site.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    // 비밀번호 검증
    public void validatePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    // 사용자명 중복 검증
    public void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
        }
    }

    // 이메일 중복 검증
    public void validateEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
    }
}
