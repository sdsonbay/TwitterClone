package com.demo.repository;

import com.demo.model.Tweet;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    @Query("SELECT t FROM Tweet t WHERE tweet_user_userid = ?1 ORDER BY updatedAt DESC")
    List<Tweet> findLatestTweetByUser(Long userId);
    @Query(
            value = "SELECT * FROM tweet WHERE tweet_user_userid IN (SELECT followee FROM follower WHERE follower = ?1) ORDER BY updatedAt DESC",
            nativeQuery = true
    )
    List<Tweet> findTweetsThatUserFollows(User user);
}
