package cz.upce.fei.nnpda_app.dto.Project;

import cz.upce.fei.nnpda_app.enums.ProjectStatus;
import cz.upce.fei.nnpda_app.model.Comment;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequestUpdateDto {
    private String name;
    private String description;
    private ProjectStatus status;
}
