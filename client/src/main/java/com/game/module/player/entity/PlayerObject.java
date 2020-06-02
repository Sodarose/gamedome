package com.game.module.player.entity;

import com.game.module.item.entity.Bag;
import com.game.module.item.entity.EquipBar;
import com.game.module.player.model.Property;
import com.game.protocol.PlayerProtocol;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/1 9:52
 */
@Data
public class PlayerObject {
    private Integer id;
    private String  name;
    private Integer level;
    private Integer career;
    private Integer sceneId;
    private PlayerProtocol.PropertyInfo propertyInfo;
    private Bag bag;
    private EquipBar equipBar;

    public PlayerObject(){

    }

    public PlayerObject(PlayerProtocol.PlayerInfo playerInfo){
        this.id = playerInfo.getId();
        this.name = playerInfo.getName();
        this.level = playerInfo.getLevel();
        this.career = playerInfo.getCareer();
        this.sceneId = playerInfo.getSceneId();
        this.propertyInfo = playerInfo.getPropertyInfo();
        Bag bag = new Bag(playerInfo.getBagInfo());
        this.bag = bag;
        EquipBar equipBar = new EquipBar(playerInfo.getEquipBar());
        this.equipBar = equipBar;
    }
}
