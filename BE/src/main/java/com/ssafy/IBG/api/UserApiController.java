package com.ssafy.IBG.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.IBG.api.user.*;
import com.ssafy.IBG.domain.Interest;
import com.ssafy.IBG.api.dto.Result;
import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final InterestService interestService;
    private final ScoreService scoreService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RESTAPIService restapiService;

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 유저 회원 가입
     **/
    @PostMapping("/join")
    public Result join(@RequestBody UserJoinRequest request){

        User user = new User();
        String encPwd = bCryptPasswordEncoder.encode(request.getUserPwd());

        user.setUserEmail(request.getUserEmail());
        user.setUserNick(request.getUserNick());
        user.setUserPwd(encPwd);

        if(userService.join(user)){
            return new Result(HttpStatus.OK.value());
        }else{
            return new Result(HttpStatus.CONFLICT.value());
        }
    }

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 회원가입 시 이메일 중복 체크
     **/
    @PostMapping("/email")
    public Result emailVerify(@RequestBody UserConfirmRequest request){

        if(userService.confirmEmail(request.getUserEmail()))
            return new Result(HttpStatus.OK.value());

        return new Result(HttpStatus.CONFLICT.value());
    }

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 회원가입 시 닉네임 중복 체크
     **/
    @PostMapping("/nickname")
    public Result nickVerify(@RequestBody UserConfirmRequest request){
        if(userService.confirmNick(request.getUserNick()))
            return new Result(HttpStatus.OK.value());

        return new Result(HttpStatus.CONFLICT.value());
    }

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 회원번호로 회원 정보 조회하기
     **/
    @GetMapping("/user/account/{userNo}")
    public Result getUserInfo(@PathVariable("userNo") Integer userNo){
        User user = userService.getUserByUserNo(userNo);

        if(user == null)
            return new Result(HttpStatus.CONFLICT.value());

        UserInfoResponse response = new UserInfoResponse(user.getUserNo(), user.getUserEmail(), user.getUserNick());
        return new Result(HttpStatus.OK.value(), response);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 유저의 게임별 좋아요 등록 및 해제
     **/
    @PostMapping("/user/like")
    public Result setInterestTransaction(@RequestBody UserInterestRequest request){

        if(interestService.setInterestTransaction(request.getUserNo(), request.getGameNo()))
            return new Result(HttpStatus.OK.value());

        return new Result(HttpStatus.CONFLICT.value());
    }

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 유저가 좋아요한 목록 가져오기
     * @modify :
     * - author : 박민주
     * - date : 2022-03-25 오전 11:45
     * - desc : gameNo에 대한 user isLike 전달
     **/
    @GetMapping("/user/like/{userNo}")
    public Result getInterestListByUserNo(@PathVariable("userNo") Integer userNo){
        List<Interest> list = interestService.getInterestList(userNo);

        if(list.isEmpty())
            return new Result(HttpStatus.NO_CONTENT.value());

        List<UserInterestResponse> collect = list.stream()
                .map(i -> {
                    /** game에 대한 user like 전달 **/
                    return new UserInterestResponse(i.getGame(), true);
                })
                .collect(Collectors.toList());

        return new Result(HttpStatus.OK.value(), collect);
    }

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 유저의 게임별 별점 등록 및 수정
     * @modify :
     * - author : 박민주
     * - date : 2022-04-01 오후 3:47
     * - desc : 평점 개수가 10개 이상일 경우 장고를 이용한 추천
     **/
    @PostMapping("/user/score")
    public Result setScore(@RequestBody UserScoreRequest request) throws JsonProcessingException {
        if(!scoreService.registScore(request.getUserNo(), request.getGameNo(), request.getScoreRating()))
            return new Result(HttpStatus.CONFLICT.value());

        if(scoreService.getScoreCnt(request.getUserNo()) == 10){
            System.out.println("유저의 평점 데이터가 10보다 크니 장고 실행");
            List<Integer> gameNoList = restapiService.requestGETAPI("/user3", request.getUserNo());

        }

        return new Result(HttpStatus.OK.value());
    }

}