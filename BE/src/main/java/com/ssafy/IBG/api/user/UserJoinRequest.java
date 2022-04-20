package com.ssafy.IBG.api.user;

import lombok.Data;

@Data
public class UserJoinRequest {
    private String userEmail;
    private String userPwd;
    private String userNick;
}
