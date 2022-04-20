package com.ssafy.IBG.api.review;

import lombok.Data;

@Data
public class ReviewRequest{
    private Integer userNo;
    private Integer gameNo;
    private String content;
}
