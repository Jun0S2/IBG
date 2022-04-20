package com.ssafy.IBG.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DealReview {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dealReviewNo;

    @ManyToOne
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dealNo")
    private Deal deal;

    private String dealReviewContent;
}
