package cz.upce.fei.nnpda_app.repository;

import cz.upce.fei.nnpda_app.model.Project;
import cz.upce.fei.nnpda_app.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findById(long id);
    Collection<Ticket> findByProjectId(long project_id);
    List<Ticket> findAllByAssignedUserId(Long userId);
}
