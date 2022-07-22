package com.demo.repository;

import com.demo.model.Follower;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @Query("SELECT f FROM Follower f WHERE f.follower = ?1")
    List<Follower> findTweetsThatUserFollows(User user);
    @Query("SELECT f FROM Follower f WHERE f.followee = ?1 AND f.follower = ?2")
    Optional<Follower> findByFolloweeAndFollower(User followee, User follower);
}
