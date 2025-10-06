package cz.upce.fei.nnpda_app.controller;

import cz.upce.fei.nnpda_app.dto.Ticket.TicketRequestDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketResponseDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketUpdateDto;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/projects/{projectId}/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(@PathVariable Long projectId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TicketResponseDto> tickets = ticketService.findAllTicketsByProjectId(projectId,user);
        log.info("Tickets found: {}", tickets);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(@PathVariable Long projectId,@Valid @RequestBody TicketRequestDto ticketRequest) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TicketResponseDto ticket = ticketService.createTicket(projectId,ticketRequest,user);
        log.info("Ticket created: {}", ticket);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long projectId,@PathVariable Long ticketId) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TicketResponseDto ticket = ticketService.findTicketById(projectId, ticketId, user);
        log.info("Ticket found: {}", ticket);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDto> updateTicket(@PathVariable Long projectId,@PathVariable Long ticketId,@Valid @RequestBody TicketUpdateDto ticketRequest) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TicketResponseDto ticket = ticketService.updateTicketById(projectId, ticketId, ticketRequest, user);
        log.info("Ticket updated: {}", ticket);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long projectId,@PathVariable Long ticketId) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ticketService.deleteTicketById(projectId, ticketId, user);
        log.info("Ticket deleted with id: {}", ticketId);
        return ResponseEntity.noContent().build();
    }


}

