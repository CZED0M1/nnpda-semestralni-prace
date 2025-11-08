package cz.upce.fei.nnpda_app.repository;

import cz.upce.fei.nnpda_app.model.Comment;
import cz.upce.fei.nnpda_app.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByProjectId(Long projectId);
    List<Comment> findByTicketId(Long ticketId);
    Optional<Comment> findOneById(long id);

    Long ticket(Ticket ticket);
}
