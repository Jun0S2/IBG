package com.ssafy.IBG.api.review;

import com.ssafy.IBG.domain.Review;
import com.ssafy.IBG.domain.Score;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class ReviewResponse{
    private int reviewNo;
    private double scoreRating;
    private String userNick;
    private String reviewContent;
    private String reviewReg;

    public ReviewResponse(Review review, Score score) {
        this.reviewNo = review.getReviewNo();
        this.scoreRating = score.getScoreRating();
        this.userNick = review.getUser().getUserNick();
        this.reviewContent = review.getReviewContent();
        this.reviewReg = review.getReviewReg();
    }
}

