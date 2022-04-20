package com.ssafy.IBG.domain;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameNo;

    @Column(unique = true)
    private String gameName;

    private String gameKorName;

    private int gameMinPlayer;

    private int gameMaxPlayer;

    private int gameMinTime;

    private int gameMaxTime;

    private int gameYear;

    private double gameTotalScore;

    private String gameImg;

    private int gameAge;

    private String gameCategory;

    private double gameWeight;

    @Column(length = 6000)
    private String gameDesc;

    @Column(length = 6000)
    private String gameKorDesc;

    @OneToMany(mappedBy = "game")
    private List<Review> review;

}
