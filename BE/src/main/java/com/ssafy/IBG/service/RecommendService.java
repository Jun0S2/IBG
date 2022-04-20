package com.ssafy.IBG.service;

import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.Recommend;
import com.ssafy.IBG.domain.RecommendCate;
import com.ssafy.IBG.domain.RecommendDesc;
import com.ssafy.IBG.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 11:00
     * @desc: 설문조사용 랜덤 게임 목록
     **/
    public List<Game> getGameForSurvey(int limit){
        List<Game> list = recommendRepository.findGameForSurvey(limit);

        // list 셔플
        Collections.shuffle(list);

        return list;
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오후 15:00
     * @desc: 리뷰 순으로 게임 추천
     **/
    public List<Game> getRecommendByReviews(int limit){
        return recommendRepository.findRecommendByReviews(limit);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오후 15:00
     * @desc: 랭킹 순으로 게임 추천(평점 기준)
     **/
    public List<Game> getRecommendByRanking(int limit){
       return recommendRepository.findRecommendByRanking(limit);
    }

    /**
    * @author : 박민주
    * @date : 2022-04-01 오후 3:56
    * @desc : 추천 테이블에 저장된 추천리스트 반환하기
    **/
    public List<Recommend> getRecommendByUserNo(int userNo){
        return recommendRepository.findRecommendByUserNo(userNo);
    }

    /**
     * @author : 권오범
     * @date : 2022-04-23 오후 15:00
     * @desc: 난이도 순으로 게임 추천
     **/
    public List<Game> getRecommendByWeight(int userNo, double weight, int limit, double percent){
        return recommendRepository.findRecommendByWeight(userNo, weight*(1.0 + percent), weight*(1.0 - percent), limit);
    }

    /**
     * @author : 권오범
     * @date : 2022-04-23 오후 15:00
     * @desc: 플레이어 수로 게임 추천
     **/
    public List<Game> getRecommendByPlayer(Integer userNo, double maxPlayers, double minPlayers, int limit, double percent) {
        return recommendRepository.findRecommendByPlayer(userNo, (int)Math.round(maxPlayers*(1.0 + percent)), (int)Math.round(minPlayers*(1.0 - percent)), limit);
    }

    /**
     * @author : 권오범
     * @date : 2022-04-03 오후 15:00
     * @desc: 플레이 시간으로 게임 추천
     **/
    public List<Game> getRecommendByPlayTime(Integer userNo, double maxPlayTime, double minPlayTime, int limit, double percent) {
        return recommendRepository.findRecommendByPlayTime(userNo, (int)Math.round(maxPlayTime*(1.0 + percent)), (int)Math.round(minPlayTime*(1.0 - percent)), limit);
    }

    /**
     * @author : 권오범
     * @date : 2022-04-03 오후 15:00
     * @desc: 추천 연령으로 게임 추천
     **/
    public List<Game> getRecommendByAge(Integer userNo, double gameAgeAvg, int limit, double percent) {
        return recommendRepository.findRecommendByAge(userNo, (int)Math.round(gameAgeAvg*(1.0 + percent)),(int)Math.round(gameAgeAvg*(1.0 - percent)),  limit);
    }

    /**
     * @author : 권오범
     * @date : 2022-04-03 오후 15:00
     * @desc: 초보자 추천 게임
     **/
    public List<Game> getRecommendByNewbie(Integer userNo, double gameAgeWeight, int limit, double percent) {
        return recommendRepository.findRecommendByNewbie(userNo, gameAgeWeight*percent, limit);
    }

    /**
     * @author : 권오범
     * @date : 2022-04-13 오후 10:50
     * @desc: 비슷한 장르의 게임
     **/
    public List<RecommendCate> getRecommendCateByGameNo(Integer gameNo) {
        return recommendRepository.findRecommendCateByGameNo(gameNo);
    }


    /**
    * @author : 박민주
    * @date : 2022-04-13 오후 10:05
    * @desc : 비슷한 유형의 게임
    **/
    public List<RecommendDesc> getRecommendDescByGameNo(int gameNo) {
        return recommendRepository.findRecommendDescByGameNo(gameNo);
    }
}
