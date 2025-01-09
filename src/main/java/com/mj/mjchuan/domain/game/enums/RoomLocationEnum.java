package com.mj.mjchuan.domain.game.enums;

import lombok.Getter;

import java.util.Random;

/**
 * @author xinruifan
 * @create 2025-01-08 16:16
 */
@Getter
public enum RoomLocationEnum {

    EAST(1,"east"),
    NORTH(2,"north"),
    WEST(3, "west"),
    SOUTH(4,"south"),
    ;


    private final int code;
    private final String desc;

    RoomLocationEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 通过 code 获取对应的枚举
    public static RoomLocationEnum fromCode(int code) {
        for (RoomLocationEnum value : RoomLocationEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum found for code: " + code);
    }

    // 通过 desc 获取对应的枚举
    public static RoomLocationEnum fromDesc(String desc) {
        for (RoomLocationEnum value : RoomLocationEnum.values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum found for desc: " + desc);
    }

    public static RoomLocationEnum getRandomLocation() {
        Random random = new Random();
        RoomLocationEnum[] values = RoomLocationEnum.values();
        return values[random.nextInt(values.length)];
    }

}
