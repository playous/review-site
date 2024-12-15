package movie.review_site.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.review_site.dto.LoginRequestDto;
import movie.review_site.dto.LoginResponseDto;
import movie.review_site.dto.RegisterRequestDto;
import movie.review_site.repository.UserRepository;
import movie.review_site.security.JwtTokenProvider;
import movie.review_site.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("로그인 = {}",loginRequestDto.toString());
        return userService.login(loginRequestDto);
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        userService.registerUser(registerRequestDto);
        log.info("회원가입 = {}",registerRequestDto.toString());
        return "회원가입이 완료되었습니다.";
    }

    @GetMapping("/clear")
    public String clear(){
        userRepository.deleteAll();
        return "삭제 완료";
    }

}
