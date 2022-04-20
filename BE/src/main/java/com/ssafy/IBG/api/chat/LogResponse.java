package com.ssafy.IBG.api.chat;

import lombok.Data;

import java.util.Date;

@Data
public class LogResponse {
    private int logNo;
    private int chatNo;
    private int userNo;
    private String userName;
    private String logContent;
    private Date logReg;

    public LogResponse(int logNo, int chatNo, int userNo, String userName, String logContent, Date logReg) {
        this.logNo = logNo;
        this.chatNo = chatNo;
        this.userNo = userNo;
        this.userName = userName;
        this.logContent = logContent;
        this.logReg = logReg;
    }
}
