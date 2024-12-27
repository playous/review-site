package movie.review_site.entity;

import jakarta.persistence.*;
import lombok.Data;
import movie.review_site.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 ID

    @Column(nullable = false, unique = true)
    private String loginId; // 로그인 ID

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role; // 사용자 역할 (ADMIN, USER)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 사용자가 작성한 댓글

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>(); // 즐겨찾기한 영화

    protected User(){}
    public User(String loginId, String password, String email,String username) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    @PrePersist
    protected void onCreate() {
        if (this.role == null) {
            this.role = Role.USER; // 기본 사용자 역할을 USER로 설정
        }
    }
}
