package com.demo.controller;

import com.demo.model.Comment;
import com.demo.response.ApiResponse;
import com.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twitter")
public class CommentController {
    private final CommentService commentService;
    private final ApiResponse apiResponse;

    @PostMapping(path = "/tweet/{tweetId}/comment", produces = "application/json")
    public ResponseEntity<Object> userMakesCommentAtTweet(@RequestBody Comment comment, Authentication authentication, @PathVariable("tweetId") Long tweetId) throws Exception{
        Comment savedComment = commentService.userMakesNewCommentAtTweet(authentication, tweetId, comment);
        apiResponse.setData(savedComment);
        apiResponse.setMessage("Comment created");

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @GetMapping(path = "/tweet/{tweetId}/comment", produces = "application/json")
    public ResponseEntity<Object> showTweetComments(@PathVariable("tweetId") Long tweetId){
        List<Comment> tweetComments = commentService.showTweetComments(tweetId);
        apiResponse.setData(tweetComments);
        apiResponse.setMessage("Tweet comments");

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/comment/{commentId}", produces = "application/json")
    public ResponseEntity<Object> deleteComment(Authentication authentication, @PathVariable("commentId") Long commentId) throws Exception{
        Comment deletedComment = commentService.deleteComment(authentication, commentId);
        apiResponse.setData(deletedComment);
        apiResponse.setMessage("Comment deleted");

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @PutMapping(path = "comment", produces = "application/json")
    public ResponseEntity<Object> updateComment(Authentication authentication, @RequestBody Comment comment) throws Exception{
        Comment updatedComment = commentService.updateComment(authentication, comment);
        apiResponse.setData(updatedComment);
        apiResponse.setMessage("Comment updated");

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

}
