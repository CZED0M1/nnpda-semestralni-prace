package cz.upce.fei.nnpda_app.dto.Ticket.TicketChange;

import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketChangeResponseDto {
    private Long id;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime updated;
    private String username;
}
