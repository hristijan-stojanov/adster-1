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

    @ManyToOne
    private User user;

    @ManyToOne
    private Ad adCommented;

    public Comment() {
    }

    public Comment(Ad adCommented) {
        this.adCommented = adCommented;
        this.timeCreated = LocalDateTime.now();
    }
}
