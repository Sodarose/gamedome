package com.game.gameserver.module.guild.entity;

import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.util.GameUUID;
import lombok.Data;
import org.apache.poi.sl.draw.geom.Guide;

/**
 * 公会实体
 *
 * @author xuewenkang
 * @date 2020/7/2 20:47
 */
@Data
public class GuildEntity {

    /** 公会id*/
    private Long id;

    /** 公会名称*/
    private String name;

    /** 公会等级*/
    private Integer level;

    /** 公会当前等级经验 */
    private Integer expr;

    /** 成员容量*/
    private Integer capacity;

    /** 公会成员 */
    private String members;

    /** 公会申请人列表 */
    private String applicants;

    /** 仓库金币 */
    private Integer golds;

    /** 公会公告*/
    private String announcement;

    public GuildEntity(){

    }

    public GuildEntity(Guild guild){
        this.id = guild.getId();
        this.name = guild.getName();
        this.level = guild.getLevel();
        this.expr = guild.getExpr();
        this.capacity = guild.getCapacity();
        this.members = "";
        this.applicants="";
        this.golds = guild.getGolds();
        this.announcement = guild.getAnnouncement();
    }
}
