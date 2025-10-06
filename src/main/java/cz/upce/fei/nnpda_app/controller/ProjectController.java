package cz.upce.fei.nnpda_app.controller;

import cz.upce.fei.nnpda_app.dto.Project.ProjectRequestDto;
import cz.upce.fei.nnpda_app.dto.Project.ProjectRequestUpdateDto;
import cz.upce.fei.nnpda_app.dto.Project.ProjectResponseDto;
import cz.upce.fei.nnpda_app.model.Project;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProjectResponseDto> projects = this.projectService.findAllProjects(user.getId());
        log.info("Projects found: {}", projects);
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDto projectRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project project = projectService.createProject(projectRequestDto, user);
        log.info("Project created: {}", project);
        return ResponseEntity.ok().body(project.toResponseDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProjectResponseDto project = projectService.findProjectById(id, user);
        log.info("Project found: {}", project);
        return ResponseEntity.ok().body(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectRequestUpdateDto projectRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project project = projectService.updateProject(id, projectRequestDto, user);
        log.info("Project updated: {}", project);
        return ResponseEntity.ok().body(project.toResponseDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectService.deleteProject(id, user);
        log.info("Project deleted with id: {}", id);
        return ResponseEntity.ok().body("Project deleted");
    }


}
