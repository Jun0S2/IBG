package com.ssafy.IBG.api;

import com.ssafy.IBG.api.chat.ChatListByUserResponse;
import com.ssafy.IBG.api.chat.ChatLogSaveRequest;
import com.ssafy.IBG.api.chat.LogResponse;
import com.ssafy.IBG.api.deal.DealResponse;
import com.ssafy.IBG.api.dto.Result;
import com.ssafy.IBG.domain.Chat;
import com.ssafy.IBG.domain.Deal;
import com.ssafy.IBG.domain.Log;
import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.service.ChatService;
import com.ssafy.IBG.service.DealService;
import com.ssafy.IBG.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;
    private final UserService userService;
    private final DealService dealService;

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 9:34
     * @desc : 채팅 내용 저장
    **/
    @PostMapping("/chat")
    public Result saveChatLog(@RequestBody ChatLogSaveRequest chatLogSaveRequest) {

        // 해당 유저와 채팅 내역 확인
        Chat chat = chatService.getChatByDealNoAndUserNo(chatLogSaveRequest.getDealNo(), chatLogSaveRequest.getDealUserNo());
        // 채팅 기록이 없는 경우 새로 만듦.
        if(chat == null) {
            boolean isSaved = chatService.setNewChat(chatLogSaveRequest.getDealNo(), chatLogSaveRequest.getDealUserNo());
            if(!isSaved) return new Result(HttpStatus.SERVICE_UNAVAILABLE.value());
            // 저장 후 chat 가져옴
            chat = chatService.getChatByDealNoAndUserNo(chatLogSaveRequest.getDealNo(), chatLogSaveRequest.getDealUserNo());
        }

        // 새 채팅 내역
        Log log = new Log();
        log.setChat(chat);

        // 글 작성자
        User user = userService.getUserByUserNo(chatLogSaveRequest.getUserNo());
        log.setUser(user);
        log.setLogContent(chatLogSaveRequest.getContent());

        boolean isSaved = chatService.setLog(log);
        if(!isSaved) return new Result(HttpStatus.SERVICE_UNAVAILABLE.value());
        return new Result(HttpStatus.OK.value());
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오전 10:28
     * @desc : userNo로 채팅 리스트 불러오기 - 채팅방 불러오는 것.
    **/
    @GetMapping("/chat/{dealNo}/{userNo}")
    public Result getChatList(@PathVariable int dealNo, @PathVariable int userNo) {
        List<Chat> chatList = chatService.getChatListByUserNo(userNo);
        if(chatList.isEmpty()) return new Result(HttpStatus.NO_CONTENT.value());

        // Response로 변환
        List<ChatListByUserResponse> collect = chatList.stream()
                .map(chat -> {
                    String lastLog = chatService.getLastLog(chat.getChatNo());
                    return new ChatListByUserResponse(
                            chat.getChatNo(),
                            chat.getUser().getUserNo(),
                            chat.getDeal().getDealNo(),
                            lastLog,
                            chat.getUser().getUserNick(),
                            chat.getDeal().getDealPath(),
                            chat.getDeal().getDealTitle()
                            );
                })
                .collect(Collectors.toList());

        for(ChatListByUserResponse chat : collect) {
            // 마지막 로그
            chat.setLastLog(chatService.getLastLog(chat.getChatNo()));

            // 상대 닉네임
            User user = userService.getUserByUserNo(chat.getUserNo());
            chat.setUserName(user.getUserNick());

            Deal deal = dealService.getDealDetailByDealNo(chat.getDealNo());
            // 이미지 파일 경로
            chat.setDealPath(deal.getDealPath());
            // 거래 이름
            chat.setDealName(deal.getDealTitle());
        }
        return new Result(HttpStatus.OK.value(), collect);
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-28 오후 12:59
     * @desc : 로그 리스트 가져오기
    **/
    @GetMapping("/chat/log/{dealNo}/{userNo}")
    public Result getLogList(@PathVariable int dealNo, @PathVariable int userNo) {
        Chat chat = chatService.getChatByDealNoAndUserNo(dealNo, userNo);
        List<Log> logList = chatService.getLogListByChatNo(chat.getChatNo());
        List<LogResponse> collect = logList.stream()
                .map( log -> new LogResponse(
                        log.getLogNo(),
                        log.getChat().getChatNo(),
                        log.getUser().getUserNo(),
                        log.getUser().getUserNick(),
                        log.getLogContent(),
                        log.getLogReg()
                ))
                .collect(Collectors.toList());
        return new Result(HttpStatus.OK.value(), collect);
    }

}
