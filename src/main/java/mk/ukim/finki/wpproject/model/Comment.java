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

    public Comment(LocalDateTime timeCreated, Ad adCommented) {
        this.timeCreated = timeCreated;
        this.adCommented = adCommented;
    }
}
