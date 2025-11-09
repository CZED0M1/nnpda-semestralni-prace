package cz.upce.fei.nnpda_app.dto.Ticket;

import cz.upce.fei.nnpda_app.dto.Comment.CommentResponseDto;
import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import cz.upce.fei.nnpda_app.enums.TicketType;
import cz.upce.fei.nnpda_app.model.Comment;
import cz.upce.fei.nnpda_app.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketResponseDto {
    private Long id;
    private String title;
    private TicketType type;
    private TicketPriority priority;
    private TicketStatus status;
    private LocalDateTime updated;
    private List<Comment> comments;

    private Long assignedUserId;

    public TicketResponseDto(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.type = ticket.getType();
        this.priority = ticket.getPriority();
        this.status = ticket.getStatus();
        this.updated = ticket.getUpdated();
        this.comments = ticket.getComments();

        if (ticket.getAssignedUser() != null) {
            this.assignedUserId = ticket.getAssignedUser().getId();
        } else {
            this.assignedUserId = null;
        }
    }
}

