package com.mj.mjchuan.domain.game.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xinruifan
 * @create 2025-01-07 19:05
 */

@Data
@Entity
@Table(name = "game_round")
public class GameRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roundCount;

    private String wallCard;

    private Integer state;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private GamePlayerState gamePlayerStateE;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private GamePlayerState gamePlayerStateS;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private GamePlayerState gamePlayerStateW;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private GamePlayerState gamePlayerStateN;

    private LocalDateTime createTime;

}
