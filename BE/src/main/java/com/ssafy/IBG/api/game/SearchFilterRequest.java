package com.ssafy.IBG.api.game;

import lombok.Data;

import java.util.List;

@Data
public class SearchFilterRequest {
    /** 이상 찾기 **/
    private String gameName;
    private String gameKorName;
    private Integer gamePlayer;
    private Integer gameTime;
    private Double gameWeight;
    private Integer gameAge;
    private Double gameScore;
    private List<String> gameCategory;
    private Integer userNo;
}
