package cz.upce.fei.nnpda_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.upce.fei.nnpda_app.dto.Comment.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "app_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    private String comment;

    public CommentResponseDto toResponseDto(){
        return new CommentResponseDto(this.id, this.comment, this.user.getUsername());
    }
    @Override
    public String toString() {
        return "Comment{id=" + id + "}";
    }
}
