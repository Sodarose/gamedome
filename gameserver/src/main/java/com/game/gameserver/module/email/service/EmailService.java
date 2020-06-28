package com.game.gameserver.module.email.service;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.protocol.EmailProtocol;

/**
 * @author xuewenkang
 * @date 2020/6/15 20:41
 */
public interface EmailService {
    /**
     * 获得邮件列表
     *
     * @param playerObject
     * @return com.game.protocol.EmailProtocol.EmailListRes
     */
    EmailProtocol.EmailListRes getEmailList(PlayerObject playerObject);

    /**
     * 发送邮件
     *
     * @param playerObject
     * @param req
     * @return com.game.protocol.EmailProtocol.SendEmailRes
     */
    EmailProtocol.SendEmailRes sendEmail(PlayerObject playerObject, EmailProtocol.SendEmailReq req);
}
