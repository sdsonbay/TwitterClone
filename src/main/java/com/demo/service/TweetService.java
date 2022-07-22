package com.demo.service;

import com.demo.model.Tweet;
import com.demo.model.User;
import com.demo.repository.FollowerRepository;
import com.demo.repository.TweetRepository;
import com.demo.repository.UserRepository;
import com.demo.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final TimestampUtil timestampUtil;

    public List<Tweet> getFeed(User loggedInUser){
        Tweet latestUserTweet = null;
        List<Tweet> userTweets = tweetRepository.findLatestTweetByUser(loggedInUser.getId());
        if (userTweets.size() > 0) latestUserTweet = userTweets.get(0);

        List<Tweet> followTweets = tweetRepository.findTweetsThatUserFollows(loggedInUser);
        if (latestUserTweet != null && latestUserTweet.getUpdatedAt().after(timestampUtil.oneMinuteBackTimestamp())){
            followTweets.add(0, latestUserTweet);
        }
        return followTweets;
    }

    public Tweet createTweet(Authentication authentication, Tweet newTweet){
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        newTweet.setTweetUser(loggedInUser);
        Timestamp currentTimestamp = timestampUtil.currentTimestamp();
        newTweet.setCreatedAt(currentTimestamp);
        newTweet.setUpdatedAt(currentTimestamp);
        return tweetRepository.save(newTweet);
    }

    public Tweet updateTweet(Authentication authentication, Long tweetId, String message) throws Exception{
        Tweet tweetToUpdate = tweetRepository.findById(tweetId).orElse(null);
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        if(tweetToUpdate == null){
            throw new Exception("Tweet not found");
        }
        if(!loggedInUser.equals(tweetToUpdate.getId())){
            throw new Exception("No permission to edit this tweet");
        }
        tweetToUpdate.setMessage(message);
        tweetToUpdate.setUpdatedAt(timestampUtil.currentTimestamp());
        return tweetRepository.save(tweetToUpdate);
    }

    public Tweet deleteTweet(Authentication authentication, Long tweetId) throws Exception{
        Tweet tweetToDelete = tweetRepository.findById(tweetId).orElse(null);
        if (tweetToDelete == null){
            throw new Exception("Tweet to delete not found");
        }
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        if(loggedInUser != tweetToDelete.getTweetUser()){
            throw new Exception("You have no rights to delete this tweet");
        }
        tweetRepository.delete(tweetToDelete);
        return tweetToDelete;
    }

    public Tweet getTweet(Long tweetId) throws Exception{
        Tweet tweet = tweetRepository.findById(tweetId).orElse(null);
        if (tweet == null){
            throw new Exception("No tweet found");
        }
        return tweet;
    }

    public List<Tweet> showUserTweets(Long userId){
        List<Tweet> userTweets = tweetRepository.findLatestTweetByUser(userId);
        return userTweets;
    }
}
