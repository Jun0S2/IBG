package com.ssafy.IBG.config.auth;

import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:7777/login이 요청될 때 실행된다.

@Service
@RequiredArgsConstructor
/**
 * @author : 권오범
 * @date : 2022-03-23
 * @desc: /login으로 요청이 들어올 때 동작
 **/
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User userEntity = userRepository.findUserByUserEmail(userEmail);
        return new PrincipalDetails(userEntity);
    }
}
