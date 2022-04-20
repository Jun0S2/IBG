package com.ssafy.IBG.api;

import com.ssafy.IBG.api.dto.Result;
import com.ssafy.IBG.api.review.ReviewRequest;
import com.ssafy.IBG.api.review.ReviewResponse;
import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.Review;
import com.ssafy.IBG.domain.Score;
import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.service.GameService;
import com.ssafy.IBG.service.ReviewService;
import com.ssafy.IBG.service.ScoreService;
import com.ssafy.IBG.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;
    private final GameService gameService;
    private final UserService userService;
    private final ScoreService scoreService;

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 6:22
    * @desc: 게임 하나의 리뷰 목록
    **/
    @GetMapping("/review/{gameNo}")
    public Result getReviewList(@PathVariable("gameNo") int gameNo){
        List<Review> reviewList = reviewService.getReviewByGameNo(gameNo);
        List<ReviewResponse> collect = reviewList.stream()
                .map(rl -> {
                    Score score = scoreService.getScoreByUserNoGameNo(rl.getUser().getUserNo(), rl.getGame().getGameNo());
                    return new ReviewResponse(rl, score);
                })
                .collect(Collectors.toList());
        return new Result(HttpStatus.OK.value(), collect);
    }

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 6:22
    * @desc: 리뷰 등록
    * @modify :
    * - author : 박민주
    * - date : 2022-03-25 오후 12:15
    * - desc : 저장 성공/실패 판단하는 코드 작성
    **/
    @PostMapping("/review")
    public Result setReview(@RequestBody ReviewRequest request){
        Game game = gameService.getGameByGameNo(request.getGameNo());
        User user = userService.getUserByUserNo(request.getUserNo());
        if(game == null || user == null) return new Result(HttpStatus.CONFLICT.value());

        boolean isOk = reviewService.saveReview(new Review(user, game, request.getContent()));

        if (isOk) return new Result(HttpStatus.OK.value());
        else return new Result(HttpStatus.CONFLICT.value());
    }

}
