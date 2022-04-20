package com.ssafy.IBG.repository;

import com.ssafy.IBG.domain.Chat;
import com.ssafy.IBG.domain.Deal;
import com.ssafy.IBG.domain.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final EntityManager em;

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 9:42
     * @desc : 거래 번호와 유저 번호로 채팅 검색
    **/
    public Chat findChatByDealNoAndUserNo(int dealNo, int userNo) {
        try{
            return em.createQuery("select c from Chat c where c.deal.dealNo = :dealNo and c.user.userNo = :userNo", Chat.class)
                    .setParameter("dealNo", dealNo)
                    .setParameter("userNo", userNo)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 9:48
     * @desc : 거래번호와 유저 번호로 채팅 생성
    **/
    public boolean saveNewChat(Chat chat) {
        try{
            em.persist(chat);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 10:26
     * @desc : 채팅 로그 저장
    **/
    public boolean saveLog(Log log) {
        try {
            em.persist(log);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Chat> findChatByUserNo(int userNo) {
        return em.createQuery("select c from Chat c where c.user.userNo = :userNo or c.deal.user.userNo = :userNo", Chat.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오후 12:23
     * @desc : 채팅 로그 가져오기
    **/
    public List<Log> findChatLogByChatNo(int chatNo) {
        return em.createQuery("select l from Log l where l.chat.chatNo = :chatNo", Log.class)
                .setParameter("chatNo", chatNo)
                .getResultList();
    }
}
