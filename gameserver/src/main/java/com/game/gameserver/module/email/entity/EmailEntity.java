package com.game.gameserver.module.email.entity;

import com.game.gameserver.util.GameUUID;
import lombok.Data;

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
    /** 接受者 */
    private Long receiverId;
    /** 文字内容 */
    private String content;
    /** 附件 JSON保存的*/
    private String attachments;
    /** golds */
    private Integer golds;
    /** 邮件状态 */
    private Integer state;

    public EmailEntity(){
        this.id = GameUUID.getInstance().generate();
    }

}
