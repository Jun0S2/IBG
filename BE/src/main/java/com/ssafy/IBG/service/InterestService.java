package com.ssafy.IBG.service;

import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.Interest;
import com.ssafy.IBG.domain.User;
import com.ssafy.IBG.repository.GameRepository;
import com.ssafy.IBG.repository.InterestRepository;
import com.ssafy.IBG.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 유거자 게임을 좋아요를 선택한 데이터가 있는 지 확인하고 없다면 엔티티를 생성하고 존재한다면 엔티티를 제거한다.
     **/
    @Transactional
    public boolean setInterestTransaction(Integer userNo, Integer gameNo){

        Interest interest = interestRepository.findInterestByUserNoGameNo(userNo, gameNo);

        try{
            if(interest == null) {
                User user = userRepository.findUserByUserNo(userNo);
                Game game = gameRepository.findGameByGameNo(gameNo);

                if(user == null || game == null)
                    return false;

                interestRepository.saveInterest(new Interest(user, game));
            }
            else
                interestRepository.removeInterest(interest);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            return true;
        }
    }


    /**
     * @author : 권오범
     * @date : 2022-03-23
     * @desc: 해당 유저가 좋아요 표시한 모든 데이터를 가져온다.
     **/
    public List<Interest> getInterestList(Integer userNo){
        return interestRepository.findInterestListByUserNo(userNo);
    }

    /**
     * @author : 박민주
     * @date : 2022-03-25 오전 10:42
     * @desc : userNo와 gameNo로 좋아요 했는지 찾기
     **/
    public boolean getIsLike(Integer userNo, Integer gameNo){
        if (userNo == null || gameNo == null){
            return false;
        }
        Interest interest = interestRepository.findInterestByUserNoGameNo(userNo, gameNo);
        if (interest == null) return false;
        else return true;
    }

}
