package cz.upce.fei.nnpda_app.dto.Project;

import cz.upce.fei.nnpda_app.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus state;
}
