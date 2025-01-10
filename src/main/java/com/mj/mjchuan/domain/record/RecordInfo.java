package com.mj.mjchuan.domain.record;

import com.mj.mjchuan.domain.game.model.GameRound;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author xinruifan
 * @create 2025-01-10 14:06
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "record_info")
public class RecordInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;

    private Long roundCount;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private GameRound gameRound;

    private String handleAction;
}
