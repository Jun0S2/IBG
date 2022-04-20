package com.ssafy.IBG.jwt;

public interface JwtProperties {
    String SECRET = "ibgBack121104"; // 우리 서버만 아는 비밀 값
    int EXPORATION_TIME = 60000 * 1000; // 10일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
