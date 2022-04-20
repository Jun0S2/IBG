package com.ssafy.IBG.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private int userNo;
    private String userEmail;
}