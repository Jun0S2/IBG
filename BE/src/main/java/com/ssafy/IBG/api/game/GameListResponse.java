package com.ssafy.IBG.api.game;

import com.ssafy.IBG.domain.Game;
import lombok.Data;

@Data
public class GameListResponse{
    private int gameNo;
    private String gameName;
    private String gameKorName;
    private String gameCategory;
    private int gameMinPlayer;
    private int gameMaxPlayer;
    private double gameTotalScore;
    private String gameImg;
    private boolean isLike;

    public GameListResponse(Game game, boolean isLike) {
        this.gameNo = game.getGameNo();
        this.gameName = game.getGameName();
        this.gameKorName = game.getGameKorName();
        this.gameCategory = game.getGameCategory();
        this.gameMinPlayer = game.getGameMinPlayer();
        this.gameMaxPlayer = game.getGameMaxPlayer();
        this.gameTotalScore = game.getGameTotalScore();
        this.gameImg = game.getGameImg();
        this.isLike = isLike;
    }
}
