package cz.upce.fei.nnpda_app.controller;

import cz.upce.fei.nnpda_app.dto.Comment.CommentRequestDto;
import cz.upce.fei.nnpda_app.dto.Comment.CommentResponseDto;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("")
@AllArgsConstructor
public class CommentController {
    CommentService commentService;

    @PostMapping("/projects/{projectId}/comments")
    public ResponseEntity<CommentResponseDto> createProjectComment(@PathVariable long projectId, @RequestBody CommentRequestDto commentRequestDto) {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommentResponseDto com = commentService.createCommentForProject(projectId,commentRequestDto,user);
        URI create = URI.create("/projects/"+projectId+"/comments/"+com.getId());
        return ResponseEntity.created(create).body(com);
    }
    @PostMapping("/projects/{projectId}/tickets/{ticketId}/comments")
    public ResponseEntity<CommentResponseDto> createTicketComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable long ticketId, @PathVariable String projectId) {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommentResponseDto com = commentService.createCommentForTicket(ticketId,commentRequestDto,user);
        URI create = URI.create("/projects/"+projectId+"/tickets/"+ticketId+"/comments/" +com.getId());
        return ResponseEntity.created(create).body(com);
    }

    @GetMapping("/projects/{projectId}/comments")
    public ResponseEntity<List<CommentResponseDto>>  getProjectComments(@PathVariable long projectId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CommentResponseDto> list = commentService.findByProjectId(projectId, user);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/projects/{projectId}/tickets/{ticketId}/comments")
    public ResponseEntity<List<CommentResponseDto>>  getTicketComments(@PathVariable long projectId, @PathVariable long ticketId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CommentResponseDto> comments = commentService.findByTicketId(ticketId,user);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/projects/{projectId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateProjectComment(@PathVariable long commentId, @PathVariable long projectId,
                                                                  @RequestBody CommentRequestDto commentRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommentResponseDto comment = commentService.updateComment(commentId,commentRequestDto,user);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/projects/{projectId}/tickets/{ticketId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateTicketComment(@PathVariable long commentId, @PathVariable long projectId,
                                                                  @PathVariable long ticketId,@RequestBody CommentRequestDto commentRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommentResponseDto comment = commentService.updateComment(commentId,commentRequestDto,user);
        return ResponseEntity.ok(comment);
    }


    @DeleteMapping("/projects/{projectId}/comments/{commentId}")
    public ResponseEntity<String> deleteProjectComment(@PathVariable long commentId, @PathVariable long projectId,
                                                                  @RequestBody CommentRequestDto commentRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentService.deleteComment(commentId,user);
        return ResponseEntity.ok().body("Comment deleted");
    }

    @DeleteMapping("/projects/{projectId}/tickets/{ticketId}/comments/{commentId}")
    public ResponseEntity<String> deleteTicketComment(@PathVariable long commentId, @PathVariable long projectId,
                                                      @RequestBody CommentRequestDto commentRequestDto, @PathVariable String ticketId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentService.deleteComment(commentId,user);
        return ResponseEntity.ok().body("Comment deleted");
    }


}
