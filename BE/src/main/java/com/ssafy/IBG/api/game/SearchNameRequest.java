package com.ssafy.IBG.api.game;

import lombok.Data;

@Data
public class SearchNameRequest{
    private String searchName;
    private Integer userNo;
}