package com.game.gameserver.module.npc.objcet;

import com.game.gameserver.dictionary.entity.NpcData;
import com.game.gameserver.module.npc.entity.NpcEntity;
import com.game.gameserver.module.player.entity.PropertyEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author xuewenkang
 * @date 2020/6/2 21:50
 */
@Data
public class NpcObject {
    private int id;
    /** npc静态属性 */
    private NpcData npcData;
    /** npc动态属性 */
    private PropertyEntity propertyEntity;

    public NpcObject(int id,NpcData npcData){
        this.id = id;
        this.npcData = npcData;
    }

    public void init(){
        BeanUtils.copyProperties(propertyEntity,npcData.getPropertyData());
    }
}
