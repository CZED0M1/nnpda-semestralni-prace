package cz.upce.fei.nnpda_app.repository;

import cz.upce.fei.nnpda_app.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
