package com.game.gameserver.common;

import lombok.Data;
import lombok.Getter;

/**
 * @author xuewenkang
 * @date 2020/6/10 17:56
 */
@Getter
public class Result {
    private int code;
    private String msg;

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result createResult(int code, String msg) {
        return new Result(code, msg);
    }
}
