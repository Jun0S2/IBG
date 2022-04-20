package com.ssafy.IBG.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.IBG.api.user.UserResponse;
import com.ssafy.IBG.config.auth.PrincipalDetails;
import com.ssafy.IBG.api.dto.Result;
import com.ssafy.IBG.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 *  로그인 요청시 처리
 */
// /login 요청해서 username, password 전송하면(post)
// UsernamePasswordAuthenticationFilter가 동작
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager; // SecurityConfig에서 받아옴


    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 1. username과 password를 받아서
        Authentication authentication = null;
        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            // 로그인 시도시 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPwd());

            // 토큰으로 로그인 시도
            // PrincipalDetailsService의 loadUerByUsername() 실행
            // 로그인이 정상적으로 실행될 경우 authentication에 로그인한 정보가 담긴다.
            authentication = authenticationManager.authenticate(authenticationToken);

            // authentication이 session 영역에 저장된다. => 로그인 성공(db와 일치한다,)
//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();


            // authentication 객체가 session영역에 저장해야 하고 그 방법이 return
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하기 위해서
            // JWT 토큰을 사용하면서 세션을 만들 이유는 없지만 단지 권한 처리 때문에
            // attemptAuthentication 함수 실행 후 successfulAuthentication 함수가 실행된다.

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. 로그인 시도 authenticationManager로 로그인 시도하면 PrincipalDetailsService가 호출된다
        // loadUserByUsername 자동 실행

        // 3. 리턴되면 PrincipalDetails를 세션에 담고(권한 관리를 위해서)
        // successfulAuthentication or unsuccessfulAuthentication
        // 4. JWT 토큰을 만들어 응답
        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 해당 함수 호출
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해야 한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        System.out.println("successfulAuthentication");

        // RSA 방식이 아닌 Hash 암호방식
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPORATION_TIME))
                .withClaim("userNo", principalDetails.getUser().getUserNo()) // 넣고 싶은 key value 값
                .withClaim("userEmail", principalDetails.getUser().getUserEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // secrete 값

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX +jwtToken); // 헤더에 담겨 사용자에게 응답
        Result result = new Result(HttpStatus.OK.value(), new UserResponse(principalDetails.getUser().getUserNo(), principalDetails.getUser().getUserEmail()));
        String json = new ObjectMapper().writeValueAsString(result);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.setHeader("Access-Control-Allow-Headers", "Authorization");
        // 보통
        // 서버에서 세션 ID를 생성하고 클라이언트에게 응답해준다.
        // 요청할 때마다 쿠키값 세션 ID를 항상 들고 서버에게 요청하기 때문에
        // 서버는 세션 ID가 유효한지 판단해 유효할 경우에만 인증이 필요한 페이지에 접근 가능하게 한다.

        // 여기서
        // email, pwd가 정상이라면 JWT 토큰을 생성해 유저에게 header에 담아 응답한다.
        // 클라이언트는 JWT 토큰을 가지고 요청하며 서버에서 JWT 토큰이 유효한지 판단 -> 필터
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Result result = new Result(HttpStatus.BAD_REQUEST.value());
        String json = new ObjectMapper().writeValueAsString(result);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
