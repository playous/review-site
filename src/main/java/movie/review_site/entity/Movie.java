package movie.review_site.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    private Long id; // 영화 ID (API의 영화 ID를 사용)

    @Column(nullable = false)
    private String title; // 영화 제목

    @Column
    private String director; // 감독 이름

    @Column
    private String genre; // 장르 (예: "Action, Drama")

    @Column
    private String releaseDate; // 개봉일

    @Column
    private String posterUrl; // 포스터 이미지 URL

    @Column(columnDefinition = "TEXT")
    private String overview; // 영화 줄거리

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 영화에 달린 댓글 (1:N 관계)

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>(); // 즐겨찾기한 사용자 목록 (1:N 관계)
}
