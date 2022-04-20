package com.ssafy.IBG.repository;

import com.ssafy.IBG.domain.Deal;
import com.ssafy.IBG.domain.DealReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DealRepository {

    private final EntityManager em;

    /**
     * @author : 곽현준
     * @date : 2022-03-23 오후 5:48
     * @desc : deal 객체 저장
    **/
    public boolean saveDeal(Deal deal) {
        try {
            em.persist(deal);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-27 오후 5:34
     * @desc : 전체 deal List 가져오기
    **/
    public List<Deal> findDeal() {
        try{
            return em.createQuery("select d from Deal d", Deal.class)
                    .getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-27 오후 5:51
     * @desc : 게임 이름으로 deal List 가져오기
    **/
    public List<Deal> findDealByGameName(String gameName) {
        try {
            return em.createQuery("select d from Deal d where d.game.gameName = :gameName", Deal.class)
                    .setParameter("gameName", gameName)
                    .getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-27 오후 5:57
     * @desc : 거래 상세 가져오기
    **/
    public Deal findDealByDealNo(int dealNo) {
        try{
            return em.find(Deal.class, dealNo);
        }catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-04-05 오후 4:15
     * @desc : 거래 리뷰 등록
    **/
    public boolean saveDealReview(DealReview dealReview) {
        try{
            em.persist(dealReview);
            return true;
        }catch (NoResultException e) {
            return false;
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-04-05 오후 5:10
     * @desc : 거래 번호로 댓글 목록 가져오기
     * @modify
     * - @author : 곽현준
     * - @date : 2022-04-05 오전 10:35
     * - @desc : 댓글 순서 변경
    **/
    public List<DealReview> findDealReviewByDealNo(int dealNo) {
        try{
            return em.createQuery("select dr from DealReview dr where dr.deal.dealNo = :dealNo order by dr.dealReviewNo desc", DealReview.class)
                    .setParameter("dealNo", dealNo)
                    .getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }
}
