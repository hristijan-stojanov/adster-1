package mk.ukim.finki.wpproject.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timeCreated;

    @Column(length = 2000)
    private String content;

    @ManyToOne
    private User user;

    public Comment() {
    }

    public Comment(String content, User user) {
        timeCreated = LocalDateTime.now();
        this.content = content;
        this.user = user;
    }
}
