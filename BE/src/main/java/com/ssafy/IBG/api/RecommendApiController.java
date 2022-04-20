package com.ssafy.IBG.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.IBG.api.dto.Result;
import com.ssafy.IBG.api.recommend.RecommendResultResponse;
import com.ssafy.IBG.api.recommend.RecommendResultResponseWithTarget;
import com.ssafy.IBG.api.recommend.RecommendSurveyResponse;
import com.ssafy.IBG.domain.*;
import com.ssafy.IBG.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RecommendApiController {

    private final RecommendService recommendService;
    private final InterestService interestService;
    private final RESTAPIService restapiService;
    private final GameService gameService;
    private final ScoreService scoreService;
    private final UserService userService;

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 11:00
     * @desc: 유저에게 설문을 받을 게임 데이터 목록 전송
     * */
    @GetMapping("/user/survey")
    public Result getSurveyGameList(){

        List<Game> list = recommendService.getGameForSurvey(50);

        if(list.isEmpty())
            return new Result(HttpStatus.NO_CONTENT.value());

        List<RecommendSurveyResponse> collect = list.stream()
                .map(g -> {
                    return new RecommendSurveyResponse(g.getGameNo(), g.getGameName(), g.getGameKorName(), g.getGameImg());
                })
                .collect(Collectors.toList());

        return new Result(HttpStatus.OK.value(), collect);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 14:00
     * @desc: 리뷰 많은 순서로 추천
     * */
    @GetMapping("/game/review/{userNo}")
    public Result getRecommendByReviews(@PathVariable(name = "userNo") Integer userNo){

        List<Game> list = recommendService.getRecommendByReviews(50);

        // 데이터 못 찾을 때
        if(list.isEmpty())
            return new Result(HttpStatus.NO_CONTENT.value());

        // request.getUserNo()가 null일 때 좋아요 false로 제공
        if(userNo == 0)
            return getResultList(list, null);

        return getResultList(list, userNo);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 14:00
     * @desc: 보드 게임 랭킹 순서로 추천(평점 기준 순서)
     * @modify :
     * - author : 박민주
     * - date : 2022-04-04 오전 2:15
     * - desc : 평점 수 대비 평점으로 인기 목록 뽑기
     * */
    @GetMapping("/game/ranking/{userNo}")
    public Result getRecommendByRanking(@PathVariable(name = "userNo") Integer userNo) throws JsonProcessingException {
            // 테스트 필요
          return new Result(HttpStatus.NO_CONTENT.value());
//        List<Integer> game_no_list = restapiService.requestGETAPI("/popular");
//
//        List<Game> game_popular_list = game_no_list.stream().map(no -> gameService.getGameByGameNo(no)).collect(Collectors.toList());
//
//        return getResultList(game_popular_list, userNo);
    }

    /**
    * @author : 박민주
    * @date : 2022-04-04 오전 2:34
    * @desc : 사용자가 한 게임 중 비슷한 게임(랜덤) 유형별 추천 - Desc 기준
    **/
    @GetMapping("/game/desc/{userNo}")
    public Result getRecommendByDesc(@PathVariable(name = "userNo") Integer userNo) throws JsonProcessingException {
        List<Score> scoreList = new ArrayList<>();
        if (userNo != 0) scoreList = scoreService.getScoreByUserNo(userNo);
        int gameNo = 1;
        if (scoreList.isEmpty()) {
            double random = Math.random();
            gameNo = (int)(random*300)+1;
        }else{
            double random = Math.random();
            int n = (int)(random*scoreList.size());
            gameNo = scoreList.get(n).getGame().getGameNo();
        }

        String target = gameService.getGameByGameNo(gameNo).getGameKorName();

        List<RecommendDesc> recommendDescList = recommendService.getRecommendDescByGameNo(gameNo);

        List<Game> gameList = recommendDescList.stream()
                .map(rd -> rd.getRecGame())
                .collect(Collectors.toList());

        return getRecommendGameList(userNo, gameList, target);
    }


    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 예측 평점으로 추천
     * @modify :
     * - author : 박민주
     * - date : 2022-04-01 오후 4:01
     * - desc : 추천 테이블 평점별로 가져오기 추후)셔플해서 가져오기 추가해야한다.
     * */
    @GetMapping("/game/score/{userNo}")
    public Result getRecommendByScore(@PathVariable(name = "userNo") Integer userNo) throws JsonProcessingException {
        int scoreCnt = scoreService.getScoreCnt(userNo);
        if (scoreCnt < 10) {
            System.out.println("아직 평점 데이터 10개가 안된다.");
            return new Result(HttpStatus.OK.value(), null);
        }
        List<Recommend> recommendList = recommendService.getRecommendByUserNo(userNo);

        System.out.println("평점 데이터 충분해 추천 데이터 반환. "+ recommendList.size());

        List<Game> collect = recommendList.stream().map(r -> r.getGame()).collect(Collectors.toList());

        Collections.shuffle(collect);

        return getResultList(collect, userNo);

//        List<Score> scores = scoreService.getScoreListByUserNoOrderByRating(userNo);
//
//        if(scores.size() < 10)
//            return new Result(HttpStatus.NO_CONTENT.value());
//
//
//        List<Integer> gameNoList = restapiService.requestGETAPI("/user3", userNo);
//
//        List<Game> game_popular_list = gameNoList.stream().map(no -> gameService.getGameByGameNo(no)).collect(Collectors.toList());
//
//        Collections.shuffle(game_popular_list);
//
//        return getResultList(game_popular_list, userNo);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 난이도별 추천
     * */
    @GetMapping("/game/weight/{userNo}")
    public Result getRecommendByWeight(@PathVariable(name="userNo", required = false) Integer userNo){
        List<Score> scores = scoreService.getScoreListByUserNoOrderByRating(userNo);

        if(scores.size() == 0)
            return new Result(HttpStatus.NO_CONTENT.value());

        double weight = 0d;
        for(Score score : scores){
            weight += score.getGame().getGameWeight();
        }

        weight /= scores.size();

        double percent = 0.3;

        List<Game> list = recommendService.getRecommendByWeight(userNo, weight, 30, percent);

        Result result = getRecommendGameList(userNo, list);

        while(result == null){
            percent += 0.1;
            list = recommendService.getRecommendByWeight(userNo, weight, 30, percent);
            result = getRecommendGameList(userNo, list);
        }

        return result;
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 평가했던 게임들과 유사한 게임 추천
     * */
    @GetMapping("/game/category/{userNo}")
    public Result getRecommendByCategory(@PathVariable("userNo") Integer userNo) throws JsonProcessingException {
        List<Score> scores = scoreService.getScoreListByUserNoOrderByRating(userNo);

        // list 사이즈로 평가한 게임이 없는 경우 에러 처리리
        if(scores.size() == 0)
            return new Result(HttpStatus.NO_CONTENT.value());

        int num = (int)(Math.random()*(scores.size()));
        int game_no = scores.get(num).getGame().getGameNo();

//        List<Integer> list = restapiService.requestGETAPI("/category", game_no);
        List<RecommendCate> cateList = recommendService.getRecommendCateByGameNo(game_no);

        if(cateList == null){
            return new Result(HttpStatus.NO_CONTENT.value());
        }

        List<Game> gameList = cateList.stream()
                .map(cl -> cl.getRecGame())
                .collect(Collectors.toList());

        String target = gameService.getGameByGameNo(game_no).getGameKorName();

        return getRecommendGameList(userNo, gameList, target);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 플레이 인원수별 추천
     * */
    @GetMapping("/game/player/{userNo}")
    public Result getRecommendByPlayer(@PathVariable(name="userNo", required = false) Integer userNo){
        List<Score> scores = scoreService.getScoreListByUserNoOrderByRating(userNo);

        if(scores.size() == 0)
            return new Result(HttpStatus.NO_CONTENT.value());

        double minPlayers = 0d;
        double maxPlayers = 0d;

        for(Score score : scores){
            Game game = score.getGame();
            minPlayers += (double) game.getGameMinPlayer();
            maxPlayers += (double) game.getGameMaxPlayer();
        }
        minPlayers /= scores.size();
        maxPlayers /= scores.size();

        double percent = 0.3;

        List<Game> list = recommendService.getRecommendByPlayer(userNo, maxPlayers, minPlayers, 30, percent);
        Result result = getRecommendGameList(userNo, list);

        while(result == null){
            percent += 0.1;
            list = recommendService.getRecommendByPlayer(userNo, maxPlayers, minPlayers, 30, percent);
            result = getRecommendGameList(userNo, list);
        }

        return result;
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 플레이 시간 별 추천
     * */
    @GetMapping("/game/time/{userNo}")
    public Result getRecommendByTime(@PathVariable Integer userNo){
        List<Score> scores = scoreService.getScoreListByUserNoOrderByRating(userNo);

        if(scores.size() == 0)
            return new Result(HttpStatus.NO_CONTENT.value());

        double minPlayTime = 0d;
        double maxPlayTime = 0d;

        for(Score score : scores){
            Game game = score.getGame();
            minPlayTime += game.getGameMinTime();
            maxPlayTime += game.getGameMaxTime();
        }
        minPlayTime /= scores.size();
        maxPlayTime /= scores.size();

        double percent = 0.3;
        List<Game> list = recommendService.getRecommendByPlayTime(userNo, maxPlayTime, minPlayTime, 30, percent);
        Result result = getRecommendGameList(userNo, list);

        while(result == null){
            percent += 0.1;
            list = recommendService.getRecommendByPlayTime(userNo, maxPlayTime, minPlayTime, 30, percent);
            result = getRecommendGameList(userNo, list);
        }

        return result;
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 나이대별 추천
     * */
    @GetMapping("/game/age/{userNo}")
    public Result getRecommendByAge(@PathVariable Integer userNo){
        List<Score> scores = scoreService.getScoreListByUserNoOrderByRating(userNo);

        if(scores.size() == 0)
            return new Result(HttpStatus.NO_CONTENT.value());

        double gameAgeAvg = 0d;

        for(Score score : scores){
            Game game = score.getGame();
            gameAgeAvg += game.getGameAge();
        }
        gameAgeAvg /= scores.size();

        double percent = 0.3;
        List<Game> list = recommendService.getRecommendByAge(userNo, gameAgeAvg, 30, percent);
        Result result = getRecommendGameList(userNo, list);

        while(result == null){
            percent += 0.1;
            list = recommendService.getRecommendByAge(userNo, gameAgeAvg, 30, percent);
            result = getRecommendGameList(userNo, list);
        }

        return result;
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 초보자 추천
     * */
    @GetMapping("/game/newbie/{userNo}")
    public Result getRecommendByNewbie(@PathVariable Integer userNo){
        double gameAgeWeight = gameService.getAvgWeight();

        double percent = 0.5;
        List<Game> list = recommendService.getRecommendByNewbie(userNo, gameAgeWeight, 30, percent);
        Result result = getRecommendGameList(userNo, list);

        while(result == null){
            percent += 0.1;
            list = recommendService.getRecommendByNewbie(userNo, gameAgeWeight, 30, percent);
            result = getRecommendGameList(userNo, list);
        }

        return result;
    }

    /**
     * @author : 곽현준
     * @date : 2022-04-13 오후 11:04
     * @desc : 평점 데이터 저장하기
    **/
    @GetMapping("/game/score/all")
    public Result setScoreRecommend() throws JsonProcessingException {
        Integer MAX_USER = userService.getUserSize();
        for(int userNo = 348466; userNo <= MAX_USER; userNo++){
            // user 없는 경우 넘어감
            if(userService.getUserByUserNo(userNo) == null) continue;
            // 해당 유저의 평점 개수
            int scoreCnt = scoreService.getScoreCnt(userNo);
            // 10개 미만인 경우 넘어감
            if(scoreCnt < 10) continue;
            restapiService.requestGETAPI("/user3", userNo);
        }
        return new Result(HttpStatus.OK.value());
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 추천 게임 리스트 중 플레이해본 게임 제외
     * */
    private Result getRecommendGameList(Integer userNo, List<Game> list) {
        List<Game> recommend_list = new LinkedList<>();

        for(Game g : list){
            if(scoreService.getScoreByUserNoGameNo(userNo, g.getGameNo()).getScoreRating() == 0d){
                recommend_list.add(g);
            }
        }
        Collections.shuffle(recommend_list);

        if(recommend_list.size() < 5) {
            return null;
        }else if(recommend_list.size() < 10){
            recommend_list = recommend_list.subList(0, recommend_list.size());
        }else{
            recommend_list = recommend_list.subList(0, 10);
        }


        return getResultList(recommend_list, userNo);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 추천 게임 리스트 중 플레이해본 게임 제외, 타이틀 포함
     * */
    private Result getRecommendGameList(Integer userNo, List<Game> list, String target) {
        List<Game> recommend_list = new LinkedList<>();

        for(Game g : list){
            if(scoreService.getScoreByUserNoGameNo(userNo, g.getGameNo()).getScoreRating() == 0d){
                recommend_list.add(g);
            }
        }
        Collections.shuffle(recommend_list);

        if(recommend_list.size() < 5) {
            return null;
        }else if(recommend_list.size() < 10){
            recommend_list = recommend_list.subList(0, recommend_list.size());
        }else{
            recommend_list = recommend_list.subList(0, 10);
        }

        return getResultList(recommend_list, userNo, target);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 추천 게임 목록을 DTO로 담는 메소드
     * */
    private Result getResultList(List<Game> list, Integer userNo){
        List<RecommendResultResponse> collect = list.stream()
                .map(g-> {
                    if(userNo == null)
                        return new RecommendResultResponse(g, false);

                    return new RecommendResultResponse(g, interestService.getIsLike(userNo, g.getGameNo()));
                })
                .collect(Collectors.toList());
        return new Result(HttpStatus.OK.value(), collect);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     * @desc: 추천 게임 목록을 DTO로 담는 메소드, 타겟 타이틀과 함께
     * */
    private Result getResultList(List<Game> list, Integer userNo, String target){
        List<RecommendResultResponse> collect = list.stream()
                .map(g-> {
                    return new RecommendResultResponse(g, interestService.getIsLike(userNo, g.getGameNo()));
                })
                .collect(Collectors.toList());
        return new Result(HttpStatus.OK.value(), new RecommendResultResponseWithTarget(target, collect));
    }
}
