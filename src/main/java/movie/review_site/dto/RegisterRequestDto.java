package movie.review_site.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @NotNull(message = "로그인 Id는 필수 값 입니다.")
    private String loginId;
    private String username;
    private String password;
    private String confirmPassword; // 비밀번호 확인 (선택 사항)
    private String email;
}
