package cz.upce.fei.nnpda_app.dto.Project;

import cz.upce.fei.nnpda_app.model.Comment;
import cz.upce.fei.nnpda_app.model.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequestDto {
    private String name;
    private String description;

    public Project toProject() {
        Project project = new Project();
        project.setName(this.name);
        project.setDescription(this.description);
        return project;
    }
}
