package cz.upce.fei.nnpda_app.dto.Ticket;

import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import cz.upce.fei.nnpda_app.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssignedTicketDto {
    private Long id;
    private String title;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime updated;
    private TicketType type;
    private Long projectId;
}

