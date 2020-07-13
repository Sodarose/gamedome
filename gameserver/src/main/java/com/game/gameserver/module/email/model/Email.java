package com.game.gameserver.module.email.model;

import com.game.gameserver.module.item.model.Item;

import java.util.List;

/**
 * 邮件模型
 *
 * @author xuewenkang
 * @date 2020/7/13 20:21
 */
public class Email {
    private Long id;
    /** 标题 */
    private String title;
    /** 发送者 */
    private Long senderId;
    /** 接受者 */
    private Long receiverId;
    /** 文字内容 */
    private String content;
    /** golds */
    private Integer golds;
    /** 邮件状态 */
    private Integer state;
    /** 附件*/
    private List<Item> attachments;
}
