package com.ssafy.IBG.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Data
@Table
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewNo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "gameNo")
    private Game game;

    @Column(length = 6000)
    private String reviewContent;

    //    @Temporal(TemporalType.TIMESTAMP)
    private String reviewReg;

    public Review(User user, Game game, String reviewContent) {
        this.user = user;
        this.game = game;
        this.reviewContent = reviewContent;
        Date now = new Date();
        SimpleDateFormat date = new SimpleDateFormat("MMM yyyy", Locale.US);
        String strDate = date.format(now);
        this.reviewReg = strDate;
    }



}
