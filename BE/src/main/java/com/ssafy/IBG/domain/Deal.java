package com.ssafy.IBG.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Deal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dealNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gameNo")
    private Game game;

    private String dealTitle;
    private String dealContent;
    private String dealFileName;
    private String dealSavedName;
    private String dealPath;
    private int dealPrice;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dealReg;

    @Column(nullable = false)
    private boolean dealStatus = false;

}
