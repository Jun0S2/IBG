package com.ssafy.IBG.api.chat;

import lombok.Data;

@Data
public class ChatLogSaveRequest {
    // 거래 번호
    private Integer dealNo;
    // 상대 유저 번호
    private Integer dealUserNo;
    // 채팅 내용
    private String content;
    // 채팅 작성자 유저 번호
    private Integer userNo;
}
