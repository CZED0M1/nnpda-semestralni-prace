package cz.upce.fei.nnpda_app.dto.Ticket;

import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import cz.upce.fei.nnpda_app.enums.TicketType;
import lombok.Data;

@Data
public class TicketUpdateDto {
        private String title;

        private TicketType type;

        private TicketPriority priority;

        private TicketStatus status;
}
