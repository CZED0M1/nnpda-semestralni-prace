package cz.upce.fei.nnpda_app.service;

import cz.upce.fei.nnpda_app.dto.Ticket.TicketChange.TicketChangeResponseDto;
import cz.upce.fei.nnpda_app.exception.NotFoundException;
import cz.upce.fei.nnpda_app.exception.OwnershipException;
import cz.upce.fei.nnpda_app.model.Comment;
import cz.upce.fei.nnpda_app.model.Ticket;
import cz.upce.fei.nnpda_app.model.TicketChange;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.TicketChangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketChangeService {
    TicketChangeRepository  ticketChangeRepository;

    public TicketChangeResponseDto findOneByTicketId(User user, Long id) {
        TicketChange ticketChange= ticketChangeRepository.findOneByTicketId(id).orElseThrow(() -> new NotFoundException("Ticket change not found"));
        Ticket ticket= ticketChange.getTicket();
        if(!ticket.getProject().getUser().getId().equals(user.getId()) &&
            !ticket.getAssignedUser().getId().equals(user.getId())) {
            throw new OwnershipException("Ticket is not yours or is not assigned to you");
        }
        return ticketChange.toResponseDto();
    }
    public List<TicketChangeResponseDto> findByTicket(Long ticketId,User user) {
        List<TicketChange> changes = ticketChangeRepository.findByTicketId(ticketId);
        Ticket ticket= changes.getFirst().getTicket();
        if(!ticket.getProject().getUser().getId().equals(user.getId()) &&
                !ticket.getAssignedUser().getId().equals(user.getId())) {
            throw new OwnershipException("Ticket is not yours or is not assigned to you");
        }
        return  changes.stream().map(TicketChange::toResponseDto).collect(Collectors.toList());
    }
}
