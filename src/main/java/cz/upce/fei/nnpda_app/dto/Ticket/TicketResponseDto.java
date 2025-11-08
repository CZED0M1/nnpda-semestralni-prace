package cz.upce.fei.nnpda_app.dto.Ticket;

import cz.upce.fei.nnpda_app.dto.Comment.CommentResponseDto;
import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import cz.upce.fei.nnpda_app.enums.TicketType;
import cz.upce.fei.nnpda_app.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TicketResponseDto {
    private Long id;
    private String title;
    private TicketType type;
    private TicketPriority priority;
    private TicketStatus status;
    private LocalDateTime updated;
    private List<Comment> comments;
}
