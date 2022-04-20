package com.ssafy.IBG.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.ssafy.IBG.domain.Game;
import com.ssafy.IBG.domain.QGame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private final EntityManager em;

    /**
     * @author : 박민주
     * @date : 2022-03-23 오후 5:51
     **/
    public List<Game> findGameBySearchGame(String searchName){
        JPQLQuery<Game> query = new JPAQuery<>(em);
        QGame qGame = new QGame("Game");
        List<Game> gameList = query.from(qGame)
                .where(qGame.gameName.contains(searchName))
                .fetch();
        return gameList;
    }

    /**
    * @author : 박민주
    * @date : 2022-04-01 오후 9:52
    **/
    public List<Game> findGameList(){
        return em.createQuery("select g from Game g", Game.class)
                .getResultList();
    }

    /**
     * @author : 박민주
     * @date : 2022-03-23 오후 5:51
     **/
    public Game findGameByGameName(String gameName){
        try{
            Game game = em.createQuery("select g from Game g where g.gameName = :gameName", Game.class)
                    .setParameter("gameName", gameName)
                    .getSingleResult();
            return game;
        }catch (NoResultException e){
            return null;
        }
    }

    /**
     * @author : 박민주
     * @date : 2022-03-23 오후 5:52
     **/
    public Game findGameByGameNo(int gameNo){
        try{
            Game game = em.find(Game.class, gameNo);
            return game;
        }catch (NoResultException e){
            return null;
        }

    }

    /**
     * @author : 박민주
     * @date : 2022-03-23 오후 5:52
     **/
    public List<Game> findGameByFilter(String gameName, String gameKorName, Integer gamePlayer, Integer gameTime, Double gameWeight, Integer gameAge, Double gameScore, List<String> gameCategory) {

        JPQLQuery<Game> query = new JPAQuery<>(em);
        QGame qGame = new QGame("Game");

        BooleanBuilder builder = new BooleanBuilder();
        if(gameName != null){
            builder.and(qGame.gameName.contains(gameName));
        }
        if (gameKorName != null){
            builder.and(qGame.gameKorName.contains(gameKorName));
        }
        if(gamePlayer != null){
            builder.and(qGame.gameMinPlayer.goe(gamePlayer));
        }
        if(gameTime != null){
            builder.and(qGame.gameMaxTime.goe(gameTime));
        }
        if(gameAge != null){
            builder.and(qGame.gameAge.loe(gameAge));
        }
        if(gameWeight != null){
            builder.and(qGame.gameWeight.loe(gameWeight));
        }
        if(gameScore != null){
            builder.and(qGame.gameTotalScore.goe(gameScore));
        }
        if(gameCategory != null){
            for (String c : gameCategory) {
                builder.and(qGame.gameCategory.contains(c));
            }
        }

        List<Game> gameList = query.from(qGame)
                .where(builder)
                .fetch();
        return gameList;
    }

    public double findAvgWeight() {
        return (Double)em.createQuery("select avg(g.gameWeight) from Game g")
                .getSingleResult();
    }

}