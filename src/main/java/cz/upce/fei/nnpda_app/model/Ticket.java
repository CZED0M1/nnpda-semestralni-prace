package cz.upce.fei.nnpda_app.model;

import cz.upce.fei.nnpda_app.dto.Ticket.TicketResponseDto;
import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import cz.upce.fei.nnpda_app.enums.TicketType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "app_tickets")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(unique = true)
    private String title;

    @Nonnull
    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Nonnull
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status= TicketStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public TicketResponseDto toDto() {
        return new TicketResponseDto(this.id, this.title,this.type, this.priority, this.status);
    }
}
