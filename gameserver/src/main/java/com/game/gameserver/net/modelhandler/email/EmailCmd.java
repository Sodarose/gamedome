package com.game.gameserver.net.modelhandler.email;

/**
 * @author xuewenkang
 * @date 2020/6/15 22:11
 */
public interface EmailCmd {
    /** 邮件列表请求 */
    short EMAIL_LIST_REQ = 1001;
    /** 发送邮件请求 */
    short SEND_EMAIL_REQ = 1002;
    /** 删除邮件请求 */
    short REMOVE_EMAIL_REQ = 1003;
    /** 同步邮件信息 */
    short SYN_EMAIL = 1004;
    /** 提取附件 */
    short EXTRACT_ATTACHMENTS = 1005;
    /** 查看邮件 */
    short CHECK_EMAIL = 1006;
}
