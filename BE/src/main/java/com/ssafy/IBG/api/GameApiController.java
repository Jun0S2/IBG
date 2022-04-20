package com.ssafy.IBG.api;

import com.ssafy.IBG.api.game.*;
import com.ssafy.IBG.api.review.ReviewResponse;
import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.api.dto.Result;
import com.ssafy.IBG.domain.Review;
import com.ssafy.IBG.domain.Score;
import com.ssafy.IBG.service.GameService;
import com.ssafy.IBG.service.InterestService;
import com.ssafy.IBG.service.ReviewService;
import com.ssafy.IBG.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GameApiController {

    private final GameService gameService;
    private final ReviewService reviewService;
    private final InterestService interestService;
    private final ScoreService scoreService;
    
    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 5:47
    * @desc: 자동 완성 검색
    * @modify :
    * - author : 박민주
    * - date : 2022-03-25 오전 11:47
    * - desc : userNo 추가
    **/
    @PostMapping("/search/auto")
    public Result getSearchAutoComplete(@RequestBody SearchNameRequest request){
        List<Game> gameList = gameService.getGameSearchGame(request.getSearchName());
        return getSearchResult(gameList, request.getUserNo());
    }

    /**
    * @author : 박민주
    * @date : 2022-04-01 오후 9:48
    * @desc : 자동완성을 위한 전체 게임 목록(이름, 번호)
    **/
    @GetMapping("/search/auto")
    public Result getSearchAutoList(){
        List<Game> gameList = gameService.getGameList();
        List<SearchAutoResponse> collect = gameList.stream()
                .map(gl -> new SearchAutoResponse(gl))
                .collect(Collectors.toList());
        return new Result(HttpStatus.OK.value(), collect);
    }

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 5:47
    * @desc: 게임 이름 검색 상세보기
    * @modify :
    * - author : 박민주
    * - date : 2022-03-25 오전 11:58
    * - desc : game에 대한 user like 전달
    * @modify :
    * - author : 박민주
    * - date : 2022-03-28 오후 5:59
    * - desc : score 및 getResult2 추가
    * @modify :
    * - author : 박민주
    * - date : 2022-03-30 오후 5:18
    * - desc : 사용자 myScore 추가
    **/
    @PostMapping("/search")
    public Result getGameByGameName(@RequestBody GameNameRequest request){
        Game game = gameService.getGameByGameName(request.getGameName());
        if(game == null){
            return new Result(HttpStatus.NO_CONTENT.value());
        }else{
            /** gameNo와 userNo **/
            boolean isLike = interestService.getIsLike(request.getUserNo(), game.getGameNo());
            Score myScore = scoreService.getScoreByUserNoGameNo(request.getUserNo(), game.getGameNo());
            return getResult2(game, isLike, myScore.getScoreRating());
        }
    }

    private Result getResult2(Game game, boolean isLike, double myScore) {
        List<Review> reviewList = reviewService.getReviewByGameNo(game.getGameNo());
        List<ReviewResponse> collect = reviewList.stream()
                .map(rl -> {
                    Score score = scoreService.getScoreByUserNoGameNo(rl.getUser().getUserNo(), rl.getGame().getGameNo());
                    return new ReviewResponse(rl, score);
                }).collect(Collectors.toList());
        return new Result(HttpStatus.OK.value(), new GameResponse(game, isLike, collect, myScore));
    }

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 5:48
    * @desc: 게임 번호 상세보기
    * @modify :
    * - author : 박민주
    * - date : 2022-03-25 오전 11:57
    * - desc : game에 대한 user like 전달
    * @modify :
    * - author : 박민주
    * - date : 2022-03-28 오후 5:59
    * - desc : score 및 getResult2 추가
    **/
    @GetMapping("/search/{gameNo}/{userNo}")
    public Result getGame(@PathVariable("gameNo") Integer gameNo, @PathVariable("userNo") Integer userNo){
        Game game = gameService.getGameByGameNo(gameNo);
        if(game == null){
            return new Result(HttpStatus.NO_CONTENT.value());
        }else{
            /** gameNo와 userNo **/
            boolean isLike = interestService.getIsLike(userNo, gameNo);
            Score myScore = scoreService.getScoreByUserNoGameNo(userNo, game.getGameNo());
            return getResult2(game, isLike, myScore.getScoreRating());
        }
    }

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 5:48
    * @desc: 검색 상세 필터
    * - author : 박민주
    * - date : 2022-03-25 오전 11:47
    * - desc : userNo 추가
    **/
    @PostMapping("/search/filter")
    public Result getGameByFilter(@RequestBody SearchFilterRequest request){
        List<Game> gameList = gameService.getGameByFilter(request.getGameName(), request.getGameKorName(), request.getGamePlayer(), request.getGameTime(), request.getGameWeight(), request.getGameAge(), request.getGameScore(), request.getGameCategory());
        return getSearchResult(gameList,request.getUserNo());

    }

    /**
    * @author : 박민주
    * @date : 2022-03-25 오전 11:49
    * @desc : game에 대한 user like 전달
    **/
    private Result getSearchResult(List<Game> gameList, Integer userNo) {
        if(gameList.isEmpty()){
            return new Result(HttpStatus.NO_CONTENT.value(), null);
        }else{
            List<GameListResponse> collect = gameList.stream()
                    .map(gl -> {
                        boolean isLike = interestService.getIsLike(userNo, gl.getGameNo());
                        return new GameListResponse(gl, isLike);
                    })
                    .collect(Collectors.toList());
            return new Result(HttpStatus.OK.value(), collect);
        }
    }

}
