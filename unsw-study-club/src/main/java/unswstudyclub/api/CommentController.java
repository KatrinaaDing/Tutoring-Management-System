package unswstudyclub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Comment;
import unswstudyclub.service.CommentService;

import java.util.UUID;

@RequestMapping("api")
@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public int addComment(@RequestBody Comment c) {
        return commentService.addComment(c);
    }

    @GetMapping("/comment/{id}")
    public Comment getCommentById(@PathVariable("id") UUID id) {
        return commentService.getCommentById(id);
    }

    @PutMapping("/comment/{id}")
    public int updateCommentById(@PathVariable("id") UUID id, @RequestBody Comment c) {
        return commentService.updateCommentById(id, c);
    }

    @DeleteMapping("/comment/{id}")
    public int deleteCommentById(@PathVariable("id") UUID id) {
        return commentService.deleteCommentById(id);
    }
}
