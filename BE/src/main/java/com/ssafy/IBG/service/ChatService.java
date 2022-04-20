package com.ssafy.IBG.service;

import com.ssafy.IBG.domain.Chat;
import com.ssafy.IBG.domain.Deal;
import com.ssafy.IBG.domain.Log;
import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.repository.ChatRepository;
import com.ssafy.IBG.repository.DealRepository;
import com.ssafy.IBG.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final DealRepository dealRepository;
    private final UserRepository userRepository;

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 9:40
     * @desc : 거래 번호와 거래 상대 유저 번호로 채팅을 검색
    **/
    public Chat getChatByDealNoAndUserNo(int dealNo, int userNo) {
        return chatRepository.findChatByDealNoAndUserNo(dealNo, userNo);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 9:47
     * @desc : 거래번호와 거래 상대로 새로운 채팅 생성
    **/
    @Transactional
    public boolean setNewChat(int dealNo, int userNo) {
        Chat chat = new Chat();
        Deal deal = dealRepository.findDealByDealNo(dealNo);
        chat.setDeal(deal);
        User user = userRepository.findUserByUserNo(userNo);
        chat.setUser(user);
        return chatRepository.saveNewChat(chat);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 10:11
     * @desc : 채팅 로그 저장
    **/
    public boolean setLog(Log log) {
        return chatRepository.saveLog(log);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 10:30
     * @desc : 거래번호와 유저 번호로 채팅 찾기
    **/
    public List<Chat> getChatListByUserNo(int userNo) {
        return chatRepository.findChatByUserNo(userNo);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오후 12:23
     * @desc : 마지막 채팅 내역 가져오기
    **/
    public String getLastLog(int chatNo) {
        List<Log> logList = chatRepository.findChatLogByChatNo(chatNo);
        return logList.get(logList.size() - 1).getLogContent();
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오후 12:43
     * @desc : 채팅 로그 가져오기
    **/
    public List<Log> getLogListByChatNo(int chatNo) {
        return chatRepository.findChatLogByChatNo(chatNo);
    }
}
