package com.ssafy.IBG.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chatNo")
    private Chat chat;

    // 채팅 친 사람
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    private String logContent;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logReg;
}
