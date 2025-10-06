package cz.upce.fei.nnpda_app.dto.Ticket;

import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketType;
import cz.upce.fei.nnpda_app.model.Ticket;
import lombok.Data;

@Data
public class TicketRequestDto {
    private String title;
    private TicketPriority priority;
    private TicketType type;

    public Ticket toTicket() {
        Ticket ticket = new Ticket();
        ticket.setTitle(this.title);
        ticket.setPriority(this.priority);
        ticket.setType(this.type);
        return ticket;
    }
}
