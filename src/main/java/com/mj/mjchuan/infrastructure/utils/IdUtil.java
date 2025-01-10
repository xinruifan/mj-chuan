package com.mj.mjchuan.infrastructure.utils;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * id工具
 *
 */
public class IdUtil {



    public static String uuid() {
        return UUID.randomUUID().toString();
    }



    public static void main(String[] args) {
        String uuid = uuid();
        System.out.println(uuid);
    }
}
