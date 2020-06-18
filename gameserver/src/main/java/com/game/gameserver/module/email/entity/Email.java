package com.game.gameserver.module.email.entity;

import lombok.Data;

/**
 *
 *
 * @author xuewenkang
 * @date 2020/6/15 20:26
 */
@Data
public class Email {
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
    /** 附件其内容格式为 类型_道具Id_数量|类型_道具Id_数量|类型_道具Id_数量|类型_道具Id_数量*/
    private String attachments;
    /** golds */
    private Long golds;
    /** 玩家Id */
    private Long playerId;
    /** 邮件状态 */
    private Integer state;
    /** 删除标志位 */
    private Integer delete;
    /** 邮件过期期限 */
    private Long expireTime;
}
