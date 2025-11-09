package cz.upce.fei.nnpda_app.repository;

import cz.upce.fei.nnpda_app.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findById(long id);
    List<Project> findByUserId(long user_id);

}
