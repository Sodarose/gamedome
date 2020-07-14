package com.game.gameserver.module.guild.entity;

import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

/**
 * 公会实体
 *
 * @author xuewenkang
 * @date 2020/7/2 20:47
 */
@Data
public class Guild {
    /**
     * 公会id
     */
    private Long id;
    /**
     * 公会名称
     */
    private String name;
    /**
     * 公会等级
     */
    private Integer level;
    /**
     * 成员容量
     */
    private Integer capacity;
    /**
     * 工会仓库容量
     */
    private Integer warehouseCapacity;
    /**
     * 金币
     */
    private Integer golds;
    /**
     * 公会公告
     */
    private String announcement;

    /**
     * 创建一个公户实体
     *
     * @param name
     * @return com.game.gameserver.module.guild.entity.Guild
     */
    public static Guild valueOf(String name) {
        Guild guild = new Guild();
        guild.setId(GameUUID.getInstance().generate());
        guild.setName(name);
        guild.setLevel(1);
        guild.setCapacity(200);
        guild.setWarehouseCapacity(64);
        guild.setGolds(0);
        guild.setAnnouncement("未有公告");
        return guild;
    }

    public void addGolds(int value) {
        this.golds += value;
    }
}
