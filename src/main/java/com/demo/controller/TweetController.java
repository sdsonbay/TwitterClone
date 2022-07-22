package com.demo.controller;

import com.demo.model.Tweet;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.response.ApiResponse;
import com.demo.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twitter")
public class TweetController {
    private final UserRepository userRepository;
    private final TweetService tweetService;
    private ApiResponse apiResponse;

    @GetMapping(path = "/tweet/feed", produces = "application/json")
    public ResponseEntity<Object> getFeed(Authentication authentication){
        User loggedInUser = userRepository.findByUsername(authentication.getName());
        List<Tweet> userFeed = tweetService.getFeed(loggedInUser);
        apiResponse.setMessage("User Feed");
        apiResponse.setData(userFeed);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @GetMapping(path = "/tweet/user/{userId}", produces = "application/json")
    public ResponseEntity<Object> showTweetsByUser(@PathVariable("userId") Long userId){
        List<Tweet> userTweets = tweetService.showUserTweets(userId);
        apiResponse.setMessage("Tweets by user");
        apiResponse.setData(userTweets);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @PostMapping(path = "/tweet/create", produces = "application/json")
    public ResponseEntity<Object> createTweet(Authentication authentication, @RequestBody Tweet newTweet){
        Tweet savedTweet = tweetService.createTweet(authentication, newTweet);
        apiResponse.setMessage("Tweet created");
        apiResponse.setData(savedTweet);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.CREATED);
    }

    @PutMapping(path = "tweet/update/{tweetId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateTweet(@PathVariable("tweetId") Long tweetId, @RequestBody @Valid Tweet tweet, Authentication authentication) throws Exception{
        Tweet updatdTweet = tweetService.updateTweet(authentication, tweetId, tweet.getMessage());
        apiResponse.setMessage("Tweet updated");
        apiResponse.setData(updatdTweet);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/tweet/{tweetId}")
    public ResponseEntity<Object> delete(@PathVariable("tweetId") Long tweetId, Authentication authentication) throws Exception{
        Tweet deletedTweet = tweetService.deleteTweet(authentication, tweetId);
        apiResponse.setMessage("Tweet deleted");
        apiResponse.setData(deletedTweet);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    @GetMapping(path = "/tweet/{tweetId}", produces = "application/json")
    public ResponseEntity<Object> getTweet(@PathVariable("tweetId") Long tweetId) throws Exception{
        Tweet tweet = tweetService.getTweet(tweetId);
        apiResponse.setMessage("Tweet");
        apiResponse.setData(tweet);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

}
