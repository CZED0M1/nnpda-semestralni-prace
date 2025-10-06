package cz.upce.fei.nnpda_app.service;

import cz.upce.fei.nnpda_app.dto.Project.ProjectRequestDto;
import cz.upce.fei.nnpda_app.dto.Project.ProjectRequestUpdateDto;
import cz.upce.fei.nnpda_app.dto.Project.ProjectResponseDto;
import cz.upce.fei.nnpda_app.exception.NotFoundException;
import cz.upce.fei.nnpda_app.exception.OwnershipException;
import cz.upce.fei.nnpda_app.model.Project;
import cz.upce.fei.nnpda_app.model.Ticket;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProjectService {
    ProjectRepository projectRepository;

    public Project createProject(ProjectRequestDto projectDto, User user) {
        Project project = projectDto.toProject();
        project.setUser(user);
        return projectRepository.save(project);
    }

    public ProjectResponseDto findProjectById(Long id,User user) {
        Project project = this.projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!project.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("Project is not yours");
        }
    return project.toResponseDto();
    }

    public List<ProjectResponseDto> findAllProjects(Long user_id) {
    List<Project> projects = projectRepository.findByUserId(user_id);
        return projects.stream().map(Project::toResponseDto).collect(Collectors.toList());
    }

    public void deleteProject(Long id,User user) {
        Project project = this.projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!project.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("Project is not yours");
        }
        projectRepository.delete(project);
    }
    public Project updateProject(Long id, ProjectRequestUpdateDto projectDto, User user) {
        Project project = this.projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!project.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("Project is not yours");
        }
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setStatus(projectDto.getStatus());
        return projectRepository.save(project);
    }
}
