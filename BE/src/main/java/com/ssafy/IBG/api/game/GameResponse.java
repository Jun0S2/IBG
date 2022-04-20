package com.ssafy.IBG.api.game;

import com.ssafy.IBG.api.review.ReviewResponse;
import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.Review;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GameResponse{
    private int gameNo;
    private String gameImg;
    private String gameName;
    private String gameKorName;
    private int gameYear;
    private double gameTotalScore;
    private boolean isLike;
    private double myScore;
    private int gameMinPlayer;
    private int gameMaxPlayer;
    private int gameMinTime;
    private int gameMaxTime;
    private double gameWeight;
    private String gameCategory;
    private String gameDesc;
    private String gameKorDesc;
    private List<ReviewResponse> ResponseReviewList;

    public GameResponse(Game game, boolean isLike, List<ReviewResponse> ResponseReviewList, double myScore) {
        this.gameNo = game.getGameNo();
        this.gameImg = game.getGameImg();
        this.gameName = game.getGameName();
        this.gameKorName = game.getGameKorName();
        this.gameYear = game.getGameYear();
        this.gameTotalScore = game.getGameTotalScore();
        this.isLike = isLike;
        this.myScore = myScore;
        this.gameMinPlayer = game.getGameMinPlayer();
        this.gameMaxPlayer = game.getGameMaxPlayer();
        this.gameMinTime = game.getGameMinTime();
        this.gameMaxTime = game.getGameMaxTime();
        this.gameWeight = game.getGameWeight();
        this.gameCategory = game.getGameCategory();
        this.gameDesc = game.getGameDesc();
        this.gameKorDesc = game.getGameKorDesc();
        this.ResponseReviewList = ResponseReviewList;
    }
}