package com.demo.repository;

import com.demo.model.Comment;
import com.demo.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(
            value = "SELECT * FROM comment WHERE comment_tweet_id_tweetid IN (SELECT id FROM tweet WHERE id = ?1) ORDER BY createdAt DESC",
            nativeQuery = true
    )
    List<Comment> findByTweetId(Tweet tweet);
}
