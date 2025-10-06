package cz.upce.fei.nnpda_app.service;

import cz.upce.fei.nnpda_app.dto.Ticket.TicketRequestDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketResponseDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketUpdateDto;
import cz.upce.fei.nnpda_app.exception.NotFoundException;
import cz.upce.fei.nnpda_app.exception.OwnershipException;
import cz.upce.fei.nnpda_app.model.Project;
import cz.upce.fei.nnpda_app.model.Ticket;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.ProjectRepository;
import cz.upce.fei.nnpda_app.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {
    private TicketRepository ticketRepository;
    private ProjectRepository projectRepository;


    public List<TicketResponseDto> findAllTicketsByProjectId(long projectId, User user) {
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));        if (!(Objects.equals(project.getUser().getId(), user.getId()))) {
            throw new OwnershipException("Project is not yours");
        }
        return project.getTickets().stream().map(Ticket::toDto).collect(Collectors.toList());
    }

    public TicketResponseDto createTicket(long projectId, TicketRequestDto ticketDto, User user) throws ChangeSetPersister.NotFoundException {
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!(Objects.equals(project.getUser().getId(), user.getId()))) {
            throw new OwnershipException("Project is not yours");
        }
        Ticket ticket = ticketDto.toTicket();

        ticket.setProject(project);
        ticketRepository.save(ticket);

        project.getTickets().add(ticket);
        projectRepository.save(project);

        return ticket.toDto();
    }

    public TicketResponseDto findTicketById(long ticketId, long projectId, User user) throws ChangeSetPersister.NotFoundException {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!(Objects.equals(project.getUser().getId(), user.getId()))) {
            throw new OwnershipException("Project is not yours");
        }

        if (!ticket.getProject().equals(project)) {
            throw new OwnershipException("Ticket does not belong to this project");
        }
        return ticket.toDto();
    }

    public void deleteTicketById(long projectId, long ticketId, User user) throws ChangeSetPersister.NotFoundException {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));        if (!(Objects.equals(project.getUser().getId(), user.getId()))) {
            throw new OwnershipException("Project is not yours");
        }

        if (!(Objects.equals(ticket.getProject().getId(), project.getId()))) {
            throw new OwnershipException("Ticket does not belong to this project");
        }
        this.ticketRepository.delete(ticket);
    }

    public TicketResponseDto updateTicketById(long projectId, long ticketId, TicketUpdateDto ticketDto, User user) throws ChangeSetPersister.NotFoundException {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!(Objects.equals(project.getUser().getId(), user.getId()))) {
            throw new OwnershipException("Project is not yours");
        }

        if (!ticket.getProject().equals(project)) {
            throw new OwnershipException("Ticket does not belong to this project");
        }

        ticket.setTitle(ticketDto.getTitle());
        ticket.setStatus(ticketDto.getStatus());
        ticket.setPriority(ticketDto.getPriority());

        ticketRepository.save(ticket);
        return ticket.toDto();
    }
}


