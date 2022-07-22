package com.demo.service;

import com.demo.model.Comment;
import com.demo.model.Tweet;
import com.demo.model.User;
import com.demo.repository.CommentRepository;
import com.demo.repository.TweetRepository;
import com.demo.repository.UserRepository;
import com.demo.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TimestampUtil timestampUtil;

    public Comment userMakesNewCommentAtTweet(Authentication authentication, Long twwetId, Comment comment)throws Exception{
        Tweet commentedTweet = tweetRepository.findById(twwetId).orElse(null);
        if(commentedTweet == null){
            throw new Exception("Tweet not found");
        }
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        comment.setCommentTweetId(commentedTweet);
        comment.setCommentUserId(loggedInUser);
        comment.setCreatedAt(timestampUtil.currentTimestamp());
        comment.setUpdatedAt(timestampUtil.currentTimestamp());
        return commentRepository.save(comment);
    }

    public Comment deleteComment(Authentication authentication, Long commentId) throws Exception{
        Comment commentToDelete = commentRepository.findById(commentId).orElse(null);
        if (commentToDelete == null){
            throw new Exception("Comment not found");
        }
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        if(commentToDelete.getCommentUserId() != loggedInUser){
            throw new Exception("Not authorized to delete this comment");
        }
        commentRepository.delete(commentToDelete);
        return commentToDelete;
    }

    public Comment updateComment(Authentication authentication, Comment comment) throws Exception{
        Comment commentToUpdate = commentRepository.findById(comment.getId()).orElse(null);
        if (commentToUpdate == null){
            throw new Exception("Comment does not exists");
        }
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        if(loggedInUser != commentToUpdate.getCommentUserId()){
            throw new Exception("Not authorized to edit this comment");
        }
        commentToUpdate.setText(comment.getText());
        commentToUpdate.setUpdatedAt(timestampUtil.currentTimestamp());

        Comment updatedComment = commentRepository.save(commentToUpdate);

        return updatedComment;
    }

    public List<Comment> showTweetComments(Long tweetId){
        Tweet commentedTweet = tweetRepository.findById(tweetId).orElse(null);
        return commentRepository.findByTweetId(commentedTweet);
    }
}
