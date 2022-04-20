package com.ssafy.IBG.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scoreNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gameNo")
    private Game game;

    private double scoreRating;

    public Score(User user, Game game, int scoreRating){
        this.user = user;
        this.game = game;
        this.scoreRating = scoreRating;
    }

}
