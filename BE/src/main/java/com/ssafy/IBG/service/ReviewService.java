package com.ssafy.IBG.service;

import com.ssafy.IBG.domain.Review;
import com.ssafy.IBG.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 6:24
    **/
    public List<Review> getReviewByGameNo(int gameNo){
        return reviewRepository.findReviewByGameNo(gameNo);
    }

    /**
    * @author : 박민주
    * @date : 2022-03-23 오후 6:24
    * @modify :
    * - author : 박민주
    * - date : 2022-03-25 오후 12:12
    * - desc : return boolean 추가
    **/
    @Transactional
    public boolean saveReview(Review review){
        return reviewRepository.saveReview(review);
    }
}
