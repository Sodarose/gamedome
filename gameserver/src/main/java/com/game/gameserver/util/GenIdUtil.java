package com.game.gameserver.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ID 生成器
 *
 * @author xuewenkang
 * @date 2020/6/5 11:15
 */
public class GenIdUtil {
    private final static AtomicInteger GEN_ID = new AtomicInteger(0);

    public static int nextId() {
        return GEN_ID.getAndIncrement();
    }
}
