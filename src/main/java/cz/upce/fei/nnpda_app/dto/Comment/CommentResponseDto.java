package cz.upce.fei.nnpda_app.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String author;
}
