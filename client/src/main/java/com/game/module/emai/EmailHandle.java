package com.game.module.emai;

import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.task.annotation.ModuleHandler;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/17 10:00
 */
@Component
@ModuleHandler(module = ModuleKey.EMAIL_MODULE)
public class EmailHandle extends BaseHandler {
    public void sendEmail(){

    }
}
