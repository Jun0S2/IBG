package com.ssafy.IBG.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatNo;

    // 거래 상대
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dealNo")
    private Deal deal;


}
