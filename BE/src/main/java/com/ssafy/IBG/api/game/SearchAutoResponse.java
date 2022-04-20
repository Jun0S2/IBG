package com.ssafy.IBG.api.game;

import com.ssafy.IBG.domain.Game;
import lombok.Data;

@Data
public class SearchAutoResponse {

    private String gameName;
    private String gameKorName;
    private int gameNo;

    public SearchAutoResponse(Game game) {
        this.gameName = game.getGameName();
        this.gameKorName = game.getGameKorName();
        this.gameNo = game.getGameNo();
    }
}
