package com.danielara.ForoHub.models.answer;
import com.danielara.ForoHub.models.topic.Topic;
import com.danielara.ForoHub.models.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Answer")
@Table(name = "answers")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Setter
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne
    private Topic topic;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "autor")
    private User author = new User();
    private Boolean isSolution = false;

    public Answer(String message, Topic topic, User author) {
        this.message = message;
        this.topic = topic;
        this.author = author;
    }
}
