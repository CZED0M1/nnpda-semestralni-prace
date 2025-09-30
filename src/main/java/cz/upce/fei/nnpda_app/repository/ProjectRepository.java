package cz.upce.fei.nnpda_app.repository;

import cz.upce.fei.nnpda_app.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
