package com.game.gameserver.module.monster.object;

import com.game.gameserver.common.entity.BaseUnit;
import com.game.gameserver.common.stage.State;
import com.game.gameserver.common.stage.StateMachine;
import com.game.gameserver.common.stage.monster.MonsterStandState;
import com.game.gameserver.dictionary.entity.MonsterData;
import com.game.gameserver.common.stage.monster.MonsterStateConfig;
import com.game.gameserver.module.player.entity.Property;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 怪物对象
 *
 * @author xuewenkang
 */
@Data
public class MonsterObject extends BaseUnit {
    private MonsterData monsterData;
    private Property property;
    private Integer sceneId;

    /**
     * 临时数据表
     */
    private Map<String, Object> ephemeraData = new HashMap<>();

    /**
     * 角色状态机
     */
    public StateMachine<MonsterObject> stateMachine;

    /** 仇恨对象 */
    private Integer hatred = null;

    private volatile int state;

    public MonsterObject(MonsterData monsterData,int sceneId) {
        super();
        this.monsterData = monsterData;
        this.sceneId = sceneId;
    }

    public void init() {
        property = new Property();
        BeanUtils.copyProperties(monsterData.getPropertyData(), property);
        ephemeraData.put(MonsterStateConfig.STAND_DURATION, 5);
        ephemeraData.put(MonsterStateConfig.PATROL_DURATION, 5);
        ephemeraData.put(MonsterStateConfig.DEATH_AWAIT_TIME,7);
        stateMachine = new StateMachine<>(this);
        stateMachine.initState(MonsterStandState.getInstance());
    }


    /**
     * 更新
     *
     * @return void
     */
    @Override
    public void update() {
        stateMachine.update();
    }

    /**
     * 重置状态
     *
     * @param
     * @return void
     */
    public void restState() {
        stateMachine.rest();
        stateMachine.initState(MonsterStandState.getInstance());
    }

    public void changeState(State<MonsterObject> state) {
        stateMachine.changeState(state);
    }



}
