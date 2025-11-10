package cz.upce.fei.nnpda_app.controller;

import cz.upce.fei.nnpda_app.dto.Ticket.AssignedTicketDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketChange.TicketChangeResponseDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketRequestDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketResponseDto;
import cz.upce.fei.nnpda_app.dto.Ticket.TicketUpdateDto;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.service.TicketChangeService;
import cz.upce.fei.nnpda_app.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/projects")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketChangeService ticketChangeService;

    @GetMapping("/{projectId}/tickets")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(@PathVariable Long projectId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TicketResponseDto> tickets = ticketService.findAllTicketsByProjectId(projectId,user);
        log.info("Tickets found: {}", tickets);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/{projectId}/tickets")
    public ResponseEntity<TicketResponseDto> createTicket(@PathVariable Long projectId,@Valid @RequestBody TicketRequestDto ticketRequest) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TicketResponseDto ticket = ticketService.createTicket(projectId,ticketRequest,user);
        log.info("Ticket created: {}", ticket);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{projectId}/tickets/{ticketId}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long projectId,@PathVariable Long ticketId) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TicketResponseDto ticket = ticketService.findTicketById(ticketId, projectId, user);
        log.info("Ticket found: {}", ticket);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{projectId}/tickets/{ticketId}/changes")
    public ResponseEntity<List<TicketChangeResponseDto>> getTicketChangesById(@PathVariable Long projectId,@PathVariable Long ticketId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TicketChangeResponseDto> changes = ticketChangeService.findByTicket(ticketId,user);
        return ResponseEntity.ok(changes);
    }

    @PutMapping("/{projectId}/tickets/{ticketId}")
    public ResponseEntity<TicketResponseDto> updateTicket(@PathVariable Long projectId,@PathVariable Long ticketId,@Valid @RequestBody TicketUpdateDto ticketRequest) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TicketResponseDto ticket = ticketService.updateTicketById(projectId, ticketId, ticketRequest, user);
        log.info("Ticket updated: {}", ticket);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{projectId}/tickets/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long projectId,@PathVariable Long ticketId) throws ChangeSetPersister.NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ticketService.deleteTicketById(projectId, ticketId, user);
        log.info("Ticket deleted with id: {}", ticketId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tickets/assigned")
    public ResponseEntity<List<AssignedTicketDto>> getAssignedTickets(
            @AuthenticationPrincipal User user) {
        log.info("Current user id: {}", user.getId());
        return ResponseEntity.ok(ticketService.getAssignedTickets(user));
    }



}

