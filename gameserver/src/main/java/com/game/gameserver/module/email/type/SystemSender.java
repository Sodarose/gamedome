package com.game.gameserver.module.email.type;

/**
 *
 * @author xuewenkang
 * @date 2020/7/13 2:06
 */
public enum SystemSender {
    GM(0L,"系统管理员"),
    AUCTION(1L,"拍卖行"),
    SYSTEM(2L,"系统");

    private long id;
    private String name;

    SystemSender(long id,String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
