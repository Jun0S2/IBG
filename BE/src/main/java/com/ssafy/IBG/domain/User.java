package com.ssafy.IBG.domain;

import com.ssafy.IBG.domain.auth.UserAuthType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(name = "userNick_unique", columnNames = {"userEmail", "userNick"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNo;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPwd;

    @Column(nullable = false)
    private String userNick;

    @Column(columnDefinition = "varchar(20) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private UserAuthType userAuth = UserAuthType.ROLE_USER;
}
