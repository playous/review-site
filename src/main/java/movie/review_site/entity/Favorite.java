package movie.review_site.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Favorite {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 즐겨찾기 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 즐겨찾기한 사용자 (User 엔티티와 관계)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie; // 즐겨찾기한 영화 (Movie 엔티티와 관계)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;// 즐겨찾기 추가 시간

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}