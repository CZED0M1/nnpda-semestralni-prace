package cz.upce.fei.nnpda_app.dto.Project;

import cz.upce.fei.nnpda_app.enums.ProjectStatus;
import lombok.Data;

@Data
public class ProjectRequestUpdateDto {
    private String name;
    private String description;
    private ProjectStatus status;
}
