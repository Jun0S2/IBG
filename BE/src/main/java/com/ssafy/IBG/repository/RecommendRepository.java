package com.ssafy.IBG.repository;

import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.Recommend;
import com.ssafy.IBG.domain.RecommendCate;
import com.ssafy.IBG.domain.RecommendDesc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecommendRepository {

    private final EntityManager em;

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 11:00
     *  limit : 30 + (랜덤하게 뽑을 게임)
     **/
    public List<Game> findGameForSurvey(int limit){
        // 기본 지정 데이터 30개
        List<Game> selectedList = em.createQuery("select g from Game g where g.gameNo < 31", Game.class)
                .getResultList();

        // 전체 데이터 수
        Long size = (Long)em.createQuery("SELECT COUNT(g) FROM Game g").getSingleResult();

        // 중복없는 난수 20개 선정
        HashSet<Integer> set = new HashSet<>();
        List<Integer> index = new ArrayList<>();
        while(set.size() < (limit-30)){
            int num = 31 + (int)(Math.random()*(size - 30));
            if(set.contains(num))
                continue;

            set.add(num);
            index.add(num);
        }

        // 랜덤하게 뽑은 게임 번호로 게임 검색
        List<Game> randomList = em.createQuery("SELECT g FROM Game g WHERE g.gameNo IN :indexs", Game.class)
                .setParameter("indexs", index)
                .getResultList();

        // 선별된 게임 + 임의의 게임
        List<Game> mergedList = new LinkedList<>();
        mergedList.addAll(selectedList);
        mergedList.addAll(randomList);

        return mergedList;
    }

    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     **/
    public List<Game> findRecommendByReviews(int limit) {
        return em.createQuery("select g from Game g order by g.review.size desc", Game.class)
                .setMaxResults(limit)
                .getResultList();
    }


    /**
     * @author : 권오범
     * @date : 2022-03-25 오전 15:00
     **/
    public List<Game> findRecommendByRanking(int limit) {
        return em.createQuery("select g from Game g order by g.gameTotalScore desc", Game.class)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
    * @author : 박민주
    * @date : 2022-04-01 오후 3:57
    **/
    public List<Recommend> findRecommendByUserNo(int userNo){
        return em.createQuery("select r from Recommend r where r.user.userNo = :userNo order by r.recommendRating desc", Recommend.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }

    public List<Game> findRecommendByWeight(int userNo, double positiveRange, double negativeRange, int limit) {
        return em.createQuery("select g from Game g where g.gameTotalScore < :positiveRange and g.gameTotalScore > :negativeRange order by g.gameTotalScore desc", Game.class)
                .setParameter("positiveRange", positiveRange)
                .setParameter("negativeRange", negativeRange)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Game> findRecommendByPlayer(Integer userNo, int maxPlayers, int minPlayers, int limit) {
        return em.createQuery("select g from Game g where g.gameMinPlayer >= :minPlayers and g.gameMaxPlayer <= :maxPlayers order by g.gameTotalScore desc", Game.class)
                .setParameter("minPlayers", minPlayers)
                .setParameter("maxPlayers", maxPlayers)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Game> findRecommendByPlayTime(Integer userNo, int maxPlayTime, int minPlayTime, int limit) {
        return em.createQuery("select g from Game g where g.gameMinTime >= :minPlayTime and g.gameMaxTime <= :maxPlayTime order by g.gameTotalScore desc", Game.class)
                .setParameter("minPlayTime", minPlayTime)
                .setParameter("maxPlayTime", maxPlayTime)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Game> findRecommendByAge(Integer userNo, int positiveRange, int negativeRange, int limit) {
        return em.createQuery("select g from Game g where g.gameAge <= :positiveRange and g.gameAge >= :negativeRange order by g.gameTotalScore desc", Game.class)
                .setParameter("positiveRange", positiveRange)
                .setParameter("negativeRange", negativeRange)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Game> findRecommendByNewbie(Integer userNo, double gameAgeWeight, int limit) {
        return em.createQuery("select g from Game g where g.gameWeight <= :gameAgeWeight order by g.gameTotalScore desc", Game.class)
                .setParameter("gameAgeWeight", gameAgeWeight)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<RecommendDesc> findRecommendDescByGameNo(int gameNo) {
        return em.createQuery("select rd from RecommendDesc rd where rd.targetGame.gameNo = :gameNo", RecommendDesc.class)
                .setParameter("gameNo", gameNo)
                .getResultList();
    }

    public List<RecommendCate> findRecommendCateByGameNo(Integer gameNo) {
        return em.createQuery("select rc from RecommendCate rc where rc.targetGame.gameNo = :gameNo", RecommendCate.class)
                .setParameter("gameNo", gameNo)
                .setMaxResults(20)
                .getResultList();
    }
}
