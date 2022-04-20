package com.ssafy.IBG.api.deal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DealReviewRequest {
    private Integer dealNo;
    private Integer userNo;
    private String dealReviewContent;
}
