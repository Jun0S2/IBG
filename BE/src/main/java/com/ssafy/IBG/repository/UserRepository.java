package com.ssafy.IBG.repository;

import com.ssafy.IBG.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    /**
     *  F01. 회원 가입
     * */
    public boolean saveUser(User user){
        try {
            em.persist(user);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     *  F01 - 1.1. email 중복 검사
     *   return 해당하는 유저의 수
     * */
    public Long emailVerify(String userEmail){
        return (Long)em.createQuery("select count(u) from User u where u.userEmail = :userEmail")
                .setParameter("userEmail", userEmail)
                .getSingleResult();
    }

    /**
     *  F01 - 1.3.닉네임 중복 확인
     *   return 해당하는 유저의 수
     * */
    public Long nickVerify(String userNick){
        return (Long)em.createQuery("select count(u) from User u where u.userNick = :userNick")
                .setParameter("userNick", userNick)
                .getSingleResult();
    }

    /**
     *  F03. 회원 정보 가져오기
     *  return 해당하는 유저의 정보
    * */
    public User findUserByUserNo(int userNo){
        try {
            return em.find(User.class, userNo);
        } catch (NoResultException e){
            return null;
        }
    }

    public User findUserByUserEmail(String userEmail){
        try {
            return em.createQuery("select u from User u where u.userEmail = :userEmail", User.class)
                    .setParameter("userEmail", userEmail)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public User findLastUser() {
        return em.createQuery("select u from User u order by u.userNo desc", User.class)
                .getResultList().get(0);
    }
}
