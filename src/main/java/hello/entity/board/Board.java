package hello.entity.board;

import hello.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Lob
    private String content;

    @CreationTimestamp
    @Column(name = "write_date")
    private LocalDateTime writeDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "mark_count")
    private int markCount;

    @Column(name = "report_count")
    private int reportCount;

    @OneToMany(mappedBy = "board")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_path")
    private String imagePath;
}