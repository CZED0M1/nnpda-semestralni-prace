package cz.upce.fei.nnpda_app.model;

import cz.upce.fei.nnpda_app.dto.Ticket.TicketResponseDto;
import cz.upce.fei.nnpda_app.enums.TicketPriority;
import cz.upce.fei.nnpda_app.enums.TicketStatus;
import cz.upce.fei.nnpda_app.enums.TicketType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "ticket",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    private LocalDateTime updated = LocalDateTime.now();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TicketChange> ticketChanges;

    public TicketResponseDto toDto() {
        return new TicketResponseDto(this);
    }
}
