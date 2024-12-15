package movie.review_site.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.review_site.dto.LoginRequestDto;
import movie.review_site.dto.LoginResponseDto;
import movie.review_site.dto.RegisterRequestDto;
import movie.review_site.entity.User;
import movie.review_site.repository.UserRepository;
import movie.review_site.security.JwtTokenProvider;
import movie.review_site.validation.UserValidation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화 서비스
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserValidation userValidation;
    @Transactional
    public String registerUser(RegisterRequestDto registerRequestDto) {

        // 1. 비밀번호 검증
        userValidation.validatePassword(registerRequestDto.getPassword(), registerRequestDto.getConfirmPassword());

        // 2. 사용자명 중복 검증
        userValidation.validateUsername(registerRequestDto.getLoginId());

        // 3. 이메일 중복 검증
        userValidation.validateEmailDuplicate(registerRequestDto.getEmail());


        // 4. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());

        // 5. 사용자 엔티티 생성
        User user = new User(registerRequestDto.getLoginId(),encodedPassword,registerRequestDto.getEmail(), registerRequestDto.getUsername());
        // 6. 사용자 저장
        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
        }

        public LoginResponseDto login(LoginRequestDto loginRequestDto){
            log.info("로그인 요청: {}", loginRequestDto.getLoginId());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getLoginId(),
                            loginRequestDto.getPassword()
                    )
            );

            log.info("인증 성공: {}", authentication.getName());

            // SecurityContext 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 토큰 생성
            String token = jwtTokenProvider.generateToken(authentication);

            log.info("JWT 생성 성공: {}", token);

            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            return new LoginResponseDto(token, user.getUsername(), user.getAuthorities().stream().findFirst().get().getAuthority());
        }


}
