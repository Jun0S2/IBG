package com.ssafy.IBG.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.IBG.config.auth.PrincipalDetails;
import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티가 filter를 가지고 있는데 그 필터 중에 BasicAuthenticationFilter이 있다.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있다.
// 만약 권한이 인증이 필요한 주소가 아니라면 필터 안탄다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // 인증이나 권한이 필요한 주소 요청이 있을 경우 => /user, /admin
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader("Authorization");

        // header가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return ;
        }

        // JWT 토큰을 검증해서 정상적인 사용자인지 확인
        // 토큰 해석
        String jwtToken = jwtHeader.replace("Bearer ","");

        // 토큰 뜯어서 유저 번호 가져옴
        int userNo = JWT.require(Algorithm.HMAC512("ibgBack121104")).build().verify(jwtToken).getClaim("userNo").asInt();

        // 서명이 정상적으로 됨
        if(userNo > 0){
            User user = userRepository.findUserByUserNo(userNo);

            PrincipalDetails principalDetails = new PrincipalDetails(user);

            // jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 security의 세션에 접근하여 Authentication 객체를 저장
            // SecurityConfig에서 지정된 url로 접근할 때 권한을 확인함
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}
