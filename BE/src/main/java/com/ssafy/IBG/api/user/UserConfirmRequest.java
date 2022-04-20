package com.ssafy.IBG.api.user;

import lombok.Data;

@Data
public class UserConfirmRequest {
    private String userEmail;
    private String userNick;
}
