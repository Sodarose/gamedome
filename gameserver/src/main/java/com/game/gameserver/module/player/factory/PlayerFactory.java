package com.game.gameserver.module.player.factory;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictRoleLevelProperty;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author xuewenkang
 * @date 2020/5/26 10:14
 */
public class PlayerFactory {

    private final static Logger logger = LoggerFactory.getLogger(PlayerFactory.class);

    public static Player createPlayerEntity(PlayerModel playerModel){
        ApplicationContext applicationContext = ServerContext.getApplication();
        DictionaryManager dictionaryManager = applicationContext.getBean(DictionaryManager.class);
        Player player = new Player();
        // 基础属性设置
        player.setId(playerModel.getId());
        player.setName(playerModel.getName());
        player.setCareer(playerModel.getCareer());
        player.setLevel(playerModel.getLevel());
        player.setSceneId(playerModel.getSceneId());
        player.setUserId(playerModel.getUserId());
        // 等级属性设置
        DictRoleLevelProperty property = dictionaryManager.getDictRoleLevelProperty(playerModel.getCareer(), playerModel.getLevel());
        if(property==null){
            logger.error("为找到该职业ID为{},等级为{}的基础属性", playerModel.getCareer(), playerModel.getLevel());
            return null;
        }
        player.setLevelProperty(property);

        // 初始化
        player.init();
        return player;
    }
}
