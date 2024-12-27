package movie.review_site.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoginRequestDto {

    private String loginId;
    private String password;
}
