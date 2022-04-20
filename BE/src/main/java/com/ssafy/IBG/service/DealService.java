package com.ssafy.IBG.service;

import com.ssafy.IBG.domain.Deal;
import com.ssafy.IBG.domain.DealReview;
import com.ssafy.IBG.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;

    /**
     * @author : 곽현준
     * @date : 2022-03-23 오후 5:49
     * @desc : deal 객체 저장
    **/
    @Transactional
    public boolean saveDeal(Deal deal) {
        return dealRepository.saveDeal(deal);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-27 오후 5:34
     * @desc : 전체 Deal List 가져오기
    **/
    public List<Deal> getDealList() {
        return dealRepository.findDeal();
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-27 오후 5:50
     * @desc : 게임 이름으로 거래 내역 가져오기
    **/
    public List<Deal> getDealListByGameName(String gameName) {
        return dealRepository.findDealByGameName(gameName);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-27 오후 5:57
     * @desc : 거래 상세 가져오기
    **/
    public Deal getDealDetailByDealNo(int dealNo) {
        return dealRepository.findDealByDealNo(dealNo);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-27 오후 6:32
     * @desc : 거래 완료
    **/
    @Transactional
    public Deal updateDealStatus(int dealNo) {
        Deal deal = dealRepository.findDealByDealNo(dealNo);
        if(deal == null) return null;
        deal.setDealStatus(true);
        return deal;
    }

    /**
     * @author : 곽현준
     * @date : 2022-04-05 오후 4:14
     * @desc : 거래 리뷰 등록
    **/
    @Transactional
    public boolean setDealReview(DealReview dealReview) {
        boolean isSaved = dealRepository.saveDealReview(dealReview);
        return isSaved;
    }

    /**
     * @author : 곽현준
     * @date : 2022-04-05 오후 5:09
     * @desc : 거래 번호로 거래 댓글 목록 가져오기
    **/
    public List<DealReview> getDealReviewByDealNo(int dealNo) {
        return dealRepository.findDealReviewByDealNo(dealNo);
    }
}
