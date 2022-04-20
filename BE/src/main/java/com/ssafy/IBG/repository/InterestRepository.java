package com.ssafy.IBG.repository;

import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.Interest;
import com.ssafy.IBG.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterestRepository {

    private final EntityManager em;

    /**
     *  관심 목록 삭제
     * */
    public void removeInterest(Interest interest){
        em.remove(interest);
    }

    /**
     *  관심 목록 저장
     * */
    public void saveInterest(Interest interest){
        em.persist(interest);
    }

    /**
     *  관심 칼럼 가져오기
     * */
    public Interest findInterestByUserNoGameNo(Integer userNo, Integer gameNo){
        try {
            return em.createQuery("select i from Interest i where i.user.userNo =: userNo and i.game.gameNo =: gameNo", Interest.class)
                    .setParameter("userNo", userNo)
                    .setParameter("gameNo", gameNo)
                    .getSingleResult();
        } catch(NoResultException e){
            return null;
        }
    }

    /**
     *  관심 목록 가져오기
     * */
    public List<Interest> findInterestListByUserNo(Integer userNo){
        return em.createQuery("select i from Interest i where i.user.userNo =: userNo", Interest.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }


}
