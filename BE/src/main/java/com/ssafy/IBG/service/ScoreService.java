package com.ssafy.IBG.service;

import com.ssafy.IBG.domain.Score;
import com.ssafy.IBG.repository.GameRepository;
import com.ssafy.IBG.repository.ScoreRepository;
import com.ssafy.IBG.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 유저가 게임을 평가한 기록이 있다면 점수를 수정하고 없다면 새롭게 엔티티를 추가한다.
     **/
    @Transactional
    public boolean registScore(Integer userNo, Integer gameNo, Integer scoreRating){
        Score score = scoreRepository.findScoreByUserNoGameNo(userNo, gameNo);

        if(score != null){
            score.setScoreRating(scoreRating);
            return true;
        }

        return scoreRepository.saveScore(new Score(userRepository.findUserByUserNo(userNo), gameRepository.findGameByGameNo(gameNo), scoreRating));
    }

    /**
    * @author : 박민주
    * @date : 2022-04-01 오후 3:49
    * @desc : 유저의 스코어 정보 개수 찾기
    **/
    public int getScoreCnt(Integer userNo){
        int scoreCntByUserNo = scoreRepository.findScoreCntByUserNo(userNo);
        return scoreCntByUserNo;
    }

    /**
    * @author : 박민주
    * @date : 2022-03-28 오후 5:53
    * @desc : userNo와 GameNo로 score 찾기
    **/
    public Score getScoreByUserNoGameNo(Integer userNo, Integer gameNo){
        Score score = scoreRepository.findScoreByUserNoGameNo(userNo, gameNo);
        if (score == null){
            score = new Score();
            score.setScoreRating(0);
            return score;
        }else return score;
    }

    /**
    * @author : 박민주
    * @date : 2022-04-04 오전 2:40
    * @desc : 유저가 평점 매긴 게임 찾기
    **/
    public List<Score> getScoreByUserNo(Integer userNo){
        return scoreRepository.findScoreByUserNo(userNo);
    }

    /**
     * @author : 권오범
     * @date : 2022-04-01 오후 03:16
     * @desc : 유저가 평가한 게임 목록 평점 순 상위 10개 가져오기
     **/
    public List<Score> getScoreListByUserNoOrderByRating(Integer userNo){
        return scoreRepository.findScoreListByUserNoOrderByRating(userNo);
    }

}
