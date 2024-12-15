package movie.review_site.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDto {
    private String loginId;
    private String username;
    private String message;
}
