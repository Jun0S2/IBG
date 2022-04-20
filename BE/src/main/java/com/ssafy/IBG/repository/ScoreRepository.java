package com.ssafy.IBG.repository;

import com.ssafy.IBG.domain.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScoreRepository {

    private final EntityManager em;

    /**
     *  Score 등록
     * */
    public boolean saveScore(Score score){
        try{
            em.persist(score);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  이전 평가 기록 찾기
     * */
    public Score findScoreByUserNoGameNo(Integer userNo, Integer gameNo){
        try {
            return em.createQuery("select s from Score s where s.user.userNo =: userNo and s.game.gameNo =: gameNo", Score.class)
                    .setParameter("userNo", userNo)
                    .setParameter("gameNo", gameNo)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    /**
    * @author : 박민주
    * @date : 2022-04-01 오후 3:59
    **/
    public int findScoreCntByUserNo(Integer userNo){
        return em.createQuery("select s from Score s where s.user.userNo = :userNo", Score.class)
                .setParameter("userNo", userNo)
                .getResultList().size();
    }

    public List<Score> findScoreByUserNo(Integer userNo) {
        return em.createQuery("select s from Score s where s.user.userNo = :userNo", Score.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }

    public List<Score> findScoreListByUserNoOrderByRating(Integer userNo){
        return em.createQuery("select s from Score s where s.user.userNo =: userNo order by s.scoreRating desc", Score.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }
}
