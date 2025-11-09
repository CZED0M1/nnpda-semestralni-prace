package cz.upce.fei.nnpda_app.service;

import cz.upce.fei.nnpda_app.dto.Comment.CommentRequestDto;
import cz.upce.fei.nnpda_app.dto.Comment.CommentResponseDto;
import cz.upce.fei.nnpda_app.exception.NotFoundException;
import cz.upce.fei.nnpda_app.exception.OwnershipException;
import cz.upce.fei.nnpda_app.model.Comment;
import cz.upce.fei.nnpda_app.model.Project;
import cz.upce.fei.nnpda_app.model.Ticket;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.CommentRepository;
import cz.upce.fei.nnpda_app.repository.ProjectRepository;
import cz.upce.fei.nnpda_app.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    CommentRepository commentRepository;
    ProjectRepository projectRepository;
    TicketRepository ticketRepository;

    public CommentResponseDto findCommentById(Long id) {
        Comment com = commentRepository.findOneById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        return com.toResponseDto();
    }

    public List<CommentResponseDto> findByProjectId(Long projectId,User user) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!project.getUser().getId().equals(user.getId())) {
            throw new  OwnershipException("You are not the owner of this project");
        }
        List<Comment> comments = commentRepository.findByProjectId(projectId);
        return  comments.stream().map(Comment::toResponseDto).collect(Collectors.toList());
    }
    public List<CommentResponseDto> findByTicketId(Long ticketId,User user) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
        if (!ticket.getAssignedUser().getId().equals(user.getId()) && !ticket.getProject().getUser().getId().equals(user.getId())) {
            throw new  OwnershipException("You are not the owner of this ticket or you are not assigned");
        }
        List<Comment> comments = commentRepository.findByTicketId(ticketId);
        return  comments.stream().map(Comment::toResponseDto).collect(Collectors.toList());
    }

    public CommentResponseDto createCommentForProject(Long projectId, CommentRequestDto commentRequestDto, User user) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!project.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("Project is not yours");
        }
        Comment comment = new Comment();
        comment.setProject(project);
        comment.setComment(commentRequestDto.getComment());
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);

        project.getComments().add(comment);
        projectRepository.save(project);

        return savedComment.toResponseDto();
    }

    public CommentResponseDto createCommentForTicket(Long ticketId, CommentRequestDto commentRequestDto, User user) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
        if (!ticket.getAssignedUser().getId().equals(user.getId()) &&
            !ticket.getProject().getUser().getId().equals(user.getId())) {
            throw new OwnershipException("Ticket is not yours or is not assigned to you");
        }
        Comment comment = new Comment();
        comment.setTicket(ticket);
        comment.setComment(commentRequestDto.getComment());
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);

        ticket.getComments().add(savedComment);
        ticketRepository.save(ticket);

        return savedComment.toResponseDto();
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("Comment is not yours");
        }
        comment.setComment(commentRequestDto.getComment());
        return commentRepository.save(comment).toResponseDto();
    }

    public void deleteComment(Long id, User user) {
        Comment  comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("Comment is not yours");
        }
        commentRepository.delete(comment);
    }
    }
