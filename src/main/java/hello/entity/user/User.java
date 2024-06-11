package hello.entity.user;

import hello.entity.board.Board;
import hello.entity.board.Bookmark;
import hello.entity.board.Comment;
import hello.entity.genre.UserGenre;
import hello.entity.prize.Entry;
import hello.entity.prize.Prize;
import hello.entity.review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity @Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_type")
    private String socialType;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "email_consent", nullable = false)
    private Boolean emailConsent;

    @Embedded
    private Address homeAddress;

    @Column(name = "join_date")
    @CreationTimestamp
    private LocalDate joinDate;

    private int point;

    @ManyToOne
    @JoinColumn(name = "recommender_id")
    private User recommender;

    @Column(name = "review_count")
    private int reviewCount;

    @Column(name = "last_draw_date")
    private LocalDate lastDrawDate;

    @Column(nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Column(name = "current_exp")
    private int currentExp;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserGenre> userGenres = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Prize> prizes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Entry> entries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
}