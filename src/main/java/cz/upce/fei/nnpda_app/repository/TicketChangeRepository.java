package cz.upce.fei.nnpda_app.repository;

import cz.upce.fei.nnpda_app.model.TicketChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketChangeRepository extends JpaRepository<TicketChange,Long> {
    List<TicketChange> findByTicketId(Long ticketId);
    Optional<TicketChange> findOneByTicketId(long id);

}
