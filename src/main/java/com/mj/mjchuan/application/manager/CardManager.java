package com.mj.mjchuan.application.manager;

import cn.hutool.json.JSONUtil;
import com.mj.mjchuan.domain.game.model.GamePlayerState;
import com.mj.mjchuan.domain.game.model.GameRound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-09 15:23
 */
public class CardManager {

    private static final int[] BARS = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final int[] TONGS = {21, 22, 23, 24, 25, 26, 27, 28, 29};
    private static final int[] WANS = {41, 42, 43, 44, 45, 46, 47, 48, 49};

    public static void createDeck(GameRound gameRound) {
        List<Integer> deck = new ArrayList<>();
        for (int i : BARS) {
            for (int j = 0; j < 4; j++) {
                deck.add(i);
            }
        }
        for (int i : TONGS) {
            for (int j = 0; j < 4; j++) {
                deck.add(i);
            }
        }
        for (int i : WANS) {
            for (int j = 0; j < 4; j++) {
                deck.add(i);
            }
        }
        Collections.shuffle(deck);
        GamePlayerState gamePlayerStateE = new GamePlayerState();
        gamePlayerStateE.setHandCard(dealHand(deck));
        gameRound.setGamePlayerStateE(gamePlayerStateE);

        GamePlayerState gamePlayerStateS = new GamePlayerState();
        gamePlayerStateS.setHandCard(dealHand(deck));
        gameRound.setGamePlayerStateS(gamePlayerStateS);

        GamePlayerState gamePlayerStateW = new GamePlayerState();
        gamePlayerStateW.setHandCard(dealHand(deck));
        gameRound.setGamePlayerStateW(gamePlayerStateW);

        GamePlayerState gamePlayerStateN = new GamePlayerState();
        gamePlayerStateN.setHandCard(dealHand(deck));
        gameRound.setGamePlayerStateN(gamePlayerStateN);

        gameRound.setWallCard(deck);
    }

    public static List<Integer> dealHand(List<Integer> deck) {
        List<Integer> hand = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            hand.add(deck.remove(0));
        }
        Collections.sort(hand);
        return hand;
    }


    public static void main(String[] args) {
        GameRound gameRound = new GameRound();
        CardManager.createDeck(gameRound);
        System.out.println(JSONUtil.toJsonStr(gameRound));
    }
}
