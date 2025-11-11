package cz.upce.fei.nnpda_app.dto.Ticket;

import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import cz.upce.fei.nnpda_app.enums.TicketType;
import cz.upce.fei.nnpda_app.model.Comment;
import lombok.Data;

import java.util.List;

@Data
public class TicketUpdateDto {
        private String title;

        private TicketType type;

        private TicketPriority priority;

        private TicketStatus status;

        private Long assignedUserId;
}
