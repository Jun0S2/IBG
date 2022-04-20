package com.ssafy.IBG.api.deal;

import com.ssafy.IBG.domain.DealReview;
import lombok.Data;

@Data
public class DealReviewResponse {
    private int dealReviewNo;
    private String userNick;
    private String dealReviewContent;

    public DealReviewResponse(DealReview dealReview) {
        this.dealReviewNo = dealReview.getDealReviewNo();
        this.userNick = dealReview.getUser().getUserNick();
        this.dealReviewContent = dealReview.getDealReviewContent();
    }
}
