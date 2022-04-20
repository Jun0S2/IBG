package com.ssafy.IBG.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table
@Entity
@Data
@NoArgsConstructor
public class RecommendCate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommendCateNo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "targetGameNo")
    private Game targetGame;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recGameNo")
    private Game recGame;


}
