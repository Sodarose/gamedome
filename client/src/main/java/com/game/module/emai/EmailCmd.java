package com.game.module.emai;

/**
 * @author xuewenkang
 * @date 2020/6/17 9:59
 */
public interface EmailCmd {
    /** 获取邮件列表 */
    short EMAIL_LIST_REQ = 1001;
    /** 发送邮件*/
    short SEND_EMAIL = 1002;
    /** 删除邮件*/
    short REMOVE_EMAIL = 1003;
    /** 删除并提取所有邮件*/
    short REMOVE_EMAIL_ALL = 1003;
    /** 删除并提取附件*/
    short REMOVE_AND_EXTRACT = 1004;
    /** 查看邮件*/
    short CHECK_EMAIL = 1005;
}
