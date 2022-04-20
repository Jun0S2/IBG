package com.ssafy.IBG.api.recommend;

import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.Recommend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendResultResponse {
    private int gameNo;
    private String gameName;
    private String gameKorName;
    private String gameCategory;
    private int gameMinPlayer;
    private int gameMaxPlayer;
    private double predictScore;
    private double gameTotalScore;
    private String gameImg;
    private boolean isLike;

    public RecommendResultResponse(Game g, boolean isLike){
        this.gameNo = g.getGameNo();
        this.gameName = g.getGameName();
        this.gameKorName = g.getGameKorName();
        this.gameCategory = g.getGameCategory();
        this.gameMinPlayer = g.getGameMinPlayer();
        this.gameMaxPlayer = g.getGameMaxPlayer();
        this.gameTotalScore = g.getGameTotalScore();
        this.gameImg = g.getGameImg();
        this.isLike = isLike;
    }

    public RecommendResultResponse(Recommend r, boolean isLike) {
        this.gameNo = r.getGame().getGameNo();
        this.gameName = r.getGame().getGameName();
        this.gameKorName = r.getGame().getGameKorName();
        this.gameCategory = r.getGame().getGameCategory();
        this.gameMinPlayer = r.getGame().getGameMinPlayer();
        this.gameMaxPlayer = r.getGame().getGameMaxPlayer();
        this.predictScore = r.getRecommendRating();
        this.gameTotalScore = r.getGame().getGameTotalScore();
        this.gameImg = r.getGame().getGameImg();
        this.isLike = isLike;
    }
}
