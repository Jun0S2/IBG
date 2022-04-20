package com.ssafy.IBG.repository;

import com.ssafy.IBG.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    /**
     * @author : 박민주
     * @date : 2022-03-23 오후 6:23
     * @desc: 게임 하나당 리뷰 목록
     * @modify :
     * - author : 박민주
     * - date : 2022-04-01 오전 1:01
     * - desc : order by 추가
     **/
    public List<Review> findReviewByGameNo(int gameNo){
        List<Review> reviewList = em.createQuery("select r from Review r where r.game.gameNo = :gameNo order by r.reviewNo desc", Review.class)
                .setParameter("gameNo", gameNo)
                .getResultList();
        if(reviewList.size() > 50)
            reviewList = reviewList.subList(0,50);
        return reviewList;
    }

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 6:23
    * @desc: 리뷰 등록
    * @modify :
    * - author : 박민주
    * - date : 2022-03-25 오후 12:11
    * - desc : return boolean 추가
    **/
    public boolean saveReview(Review review){
        try{
            em.persist(review);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
