package com.ssafy.IBG.api.recommend;

import lombok.Data;

import java.util.List;

@Data
public class RecommendResultResponseWithTarget {
    private String title;
    private List<RecommendResultResponse> recommendResultResponses;

    public RecommendResultResponseWithTarget(String title, List<RecommendResultResponse> recommendResultResponses) {
        this.title = title;
        this.recommendResultResponses = recommendResultResponses;
    }
}
