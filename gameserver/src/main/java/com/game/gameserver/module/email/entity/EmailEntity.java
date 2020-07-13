package com.game.gameserver.module.email.entity;

import com.game.gameserver.module.email.type.EmailState;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author xuewenkang
 * @date 2020/6/15 20:26
 */
@Data
public class EmailEntity {
    /** 邮件唯一ID */
    private Long id;
    /** 标题 */
    private String title;
    /** 发送者 */
    private Long senderId;
    /** 发送者名称 */
    private String senderName;
    /** 文字内容 */
    private String content;
    /** 附件*/
    private String attachments;
    /** golds */
    private Integer golds;
    /** 玩家Id */
    private Long playerId;
    /** 邮件状态 */
    private Integer state;
    /** 删除标志位 */
    private Integer delete;
    /** 邮件过期期限 */
    private Long expireTime;

    public EmailEntity(){
        this.id = GameUUID.getInstance().generate();
    }

    public EmailEntity(String title, Long sendId, String senderName, String content){
        this.id = GameUUID.getInstance().generate();
        this.title = title;
        this.senderId = sendId;
        this.senderName = senderName;
        this.content = content;
        this.state = EmailState.CLOSE;
    }

}
