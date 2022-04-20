package com.ssafy.IBG.api.deal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class DealResponse {
    private Integer dealNo;
    private Integer userNo;
    private String userNick;
    private Integer gameNo;
    private String gameName;
    private String dealTitle;
    private String dealContent;
    private String dealFileName;
    private String dealSavedName;
    private String dealPath;
    private Integer dealPrice;
    private Date dealReg;
    private boolean dealStatus;

    public DealResponse(int dealNo, int userNo, String userNick, int gameNo, String gameName, String dealTitle, String dealContent, String dealFileName, String dealSavedName, String dealPath, int dealPrice, Date dealReg, boolean dealStatus) {
        this.dealNo = dealNo;
        this.userNo = userNo;
        this.userNick = userNick;
        this.gameNo = gameNo;
        this.gameName = gameName;
        this.dealTitle = dealTitle;
        this.dealContent = dealContent;
        this.dealFileName = dealFileName;
        this.dealSavedName = dealSavedName;
        this.dealPath = dealPath;
        this.dealPrice = dealPrice;
        this.dealReg = dealReg;
        this.dealStatus = dealStatus;
    }
}
