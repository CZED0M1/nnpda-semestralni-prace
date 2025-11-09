package cz.upce.fei.nnpda_app.dto.Ticket.TicketChange;

import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketChangeRequestDto {
    private Long id;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime updated;
    private Long userId;
}
