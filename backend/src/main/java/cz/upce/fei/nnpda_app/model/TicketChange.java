package cz.upce.fei.nnpda_app.model;

import cz.upce.fei.nnpda_app.dto.Ticket.TicketChange.TicketChangeResponseDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketResponseDto;
import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="app_ticket_changes")
public class TicketChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private TicketStatus status;

    @Nonnull
    private TicketPriority priority;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    private LocalDateTime updated=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public TicketChangeResponseDto toResponseDto() {
        TicketChangeResponseDto ticketResponseDto = new TicketChangeResponseDto();
        ticketResponseDto.setId(this.id);
        ticketResponseDto.setStatus(this.status);
        ticketResponseDto.setPriority(this.priority);
        ticketResponseDto.setUpdated(this.updated);
        ticketResponseDto.setUsername(this.assignedUser.getUsername());
        return ticketResponseDto;
    }

}
