package com.ssafy.IBG.api.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.User;
import lombok.Data;

@Data
public class RecommendJsonResponse {
    @JsonProperty(value = "user_no")
    private User userNo;

    @JsonProperty(value = "game_no")
    private Game gameNo;

    @JsonProperty(value = "recommend_rating")
    private Double recommendRating;

}
