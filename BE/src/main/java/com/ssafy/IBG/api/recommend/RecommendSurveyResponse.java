package com.ssafy.IBG.api.recommend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendSurveyResponse {
    private int gameNo;
    private String gameName;
    private String gameKorName;
    private String gameImg;
}
