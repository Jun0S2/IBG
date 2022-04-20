package com.ssafy.IBG.api.user;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class UserScoreRequest {

    private Integer userNo;
    private Integer gameNo;
    private Integer scoreRating;
}
