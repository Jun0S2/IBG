package com.ssafy.IBG.api.chat;

import lombok.Data;

@Data
public class ChatListByUserResponse {
    private int chatNo;
    private int userNo;
    private int dealNo;
    private String lastLog;
    private String userName;
    private String dealPath;
    private String dealName;

    public ChatListByUserResponse(int chatNo, int userNo, int dealNo, String lastLog, String userName, String dealPath, String dealName) {
        this.chatNo = chatNo;
        this.userNo = userNo;
        this.dealNo = dealNo;
        this.lastLog = lastLog;
        this.userName = userName;
        this.dealPath = dealPath;
        this.dealName = dealName;
    }
}
