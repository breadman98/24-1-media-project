package mediaproject.its.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comment_content")
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER) // todo : 얘도 LAZY로 바꿔야하는 문제
    private User user;

//    @JoinColumn(name = "post_id")
//    @ManyToOne(fetch = FetchType.EAGER) // todo : 얘도 LAZY로 바꿔야하는 문제
//    private Post post;

    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }
}