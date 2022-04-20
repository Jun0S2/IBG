package com.ssafy.IBG.api.user;

import com.ssafy.IBG.domain.Game;
import lombok.Data;

@Data
public class UserInterestResponse {
    private int gameNo;
    private String gameImg;
    private String gameName;
    private String gameKorName;
    private double gameTotalScore;
    private int gameMinPlayer;
    private int gameMaxPlayer;
    private String gameCategory;
    private boolean isLike;

    public UserInterestResponse(Game game, boolean isLike){
        this.gameNo = game.getGameNo();
        this.gameImg = game.getGameImg();
        this.gameName = game.getGameName();
        this.gameKorName = game.getGameKorName();
        this.gameTotalScore = game.getGameTotalScore();
        this.gameMinPlayer = game.getGameMinPlayer();
        this.gameMaxPlayer = game.getGameMaxPlayer();
        this.gameCategory= game.getGameCategory();
        this.isLike = isLike;
    }
}
