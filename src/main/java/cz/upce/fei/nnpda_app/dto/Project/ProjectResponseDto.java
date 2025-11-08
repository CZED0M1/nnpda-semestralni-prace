package cz.upce.fei.nnpda_app.dto.Project;

import cz.upce.fei.nnpda_app.dto.Comment.CommentResponseDto;
import cz.upce.fei.nnpda_app.enums.ProjectStatus;
import cz.upce.fei.nnpda_app.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus state;
    private List<Comment> comments;
}
