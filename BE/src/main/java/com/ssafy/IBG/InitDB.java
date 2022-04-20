package com.ssafy.IBG;

import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.domain.auth.UserAuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    // DB Create 설정일 때만 수행
//    @PostConstruct
//    public void init(){
//        initService.dbInit();
//    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{

        private final EntityManager em;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        public void dbInit(){
            User user = setUser("admin@ssafy.com", "123", "ADMIN");
            em.persist(user);

            User testUser = setUser("test01@ssafy.com", "123", "test");
            em.persist(testUser);

        }


        private User setUser(String user_email, String user_pwd, String user_nickname) {
            User user = new User();
            user.setUserEmail(user_email);
            String rawPwd = user_pwd;
            String encPwd = bCryptPasswordEncoder.encode(rawPwd);
            user.setUserPwd(encPwd);
            // local 환경 동작 시 DB User 테이블 UTF-8 설정이 되어있지 않다면 에러가 발생함 -> 영어로 변경
            user.setUserNick(user_nickname);
            if(user_nickname.equals("ADMIN")){
                user.setUserAuth(UserAuthType.ROLE_ADMIN);
            }
            return user;
        }
    }
}
