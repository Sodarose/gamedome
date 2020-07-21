package com.game.gameserver.module.scene.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.monster.service.MonsterService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.npc.model.Npc;
import com.game.gameserver.module.npc.service.NpcService;
import com.game.gameserver.module.scene.model.GameScene;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 场景管理器
 *
 * @author xuewenkang
 * @date 2020/6/8 19:33
 */
@Component
public class SceneManager {

    private final static Logger logger = LoggerFactory.getLogger(SceneManager.class);

    /** 本地场景缓存 */
    private final static Map<Integer, GameScene> LOCAL_SCENE_MAP = new ConcurrentHashMap<>();

    public static SceneManager instance;

    /** 场景Tick线程工厂 */
    private final static ThreadFactory SCENE_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("GameScene-Tick-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();

    /** 场景TICK线程 */
    private final static ScheduledThreadPoolExecutor SCENE_TICK_THREAD = new ScheduledThreadPoolExecutor(1
            ,SCENE_THREAD_FACTORY);


    @Autowired
    private MonsterService monsterService;

    @Autowired
    private NpcService npcService;

    public SceneManager (){
        instance = this;
    }

    /** 读取场景数据创建场景 启动tick线程 */
    public void loadScene(){
        logger.info("读取场景资源数据");
        List<SceneConfig> sceneConfigList = new ArrayList<>
                (StaticConfigManager.getInstance().getSceneConfigMap().values());
        sceneConfigList.forEach(sceneConfig -> {
            GameScene scene = createScene(sceneConfig);
            LOCAL_SCENE_MAP.put(sceneConfig.getId(),scene);
        });
        actionTick();
    }

    /**
     * 场景场景
     *
     * @param sceneConfig
     * @return com.game.gameserver.module.scene.model.GameScene
     */
    private GameScene createScene(SceneConfig sceneConfig){
        GameScene scene = new GameScene(sceneConfig);
        sceneConfig.getMonsterIds().forEach(monsterId->{
           Monster monster =  monsterService.createMonster(monsterId);
           monster.setCurrScene(scene);
           scene.getMonsterMap().put(monster.getId(),monster);
        });

        sceneConfig.getNpcIds().forEach(npcId->{
            Npc npc = npcService.createNpc(npcId);
            scene.getNpcMap().put(npc.getNpcId(),npc);
        });

        return scene;
    }

    private void actionTick(){
        /** 以每秒为一帧刷新场景 */
        SCENE_TICK_THREAD.scheduleAtFixedRate(this::tick,500,1000, TimeUnit.MILLISECONDS);
    }

    private void tick(){
        try {
            LOCAL_SCENE_MAP.values().forEach(
                    scene -> {
                        // 刷新怪物状态
                        scene.getMonsterMap().forEach((key, value) -> {
                            value.update();
                        });
                        // 刷新npc状态
                        scene.getNpcMap().forEach((key, value) -> {

                        });
                        // 更新玩家状态
                        scene.getPlayerMap().forEach((key, value) -> {
                            value.update();
                        });
                        // 更新宝宝状态
                        scene.getPetMap().forEach((key, value) -> {
                            value.update();
                        });
                        // 同步客户端这一帧 场景的数据
                        NotificationHelper.syncScene(scene);
                    }
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public GameScene getScene(int sceneId){
        return LOCAL_SCENE_MAP.get(sceneId);
    }

    public void removeScene(int sceneId){
        LOCAL_SCENE_MAP.remove(sceneId);
    }

    public List<GameScene> getSceneList(){
        return new ArrayList<>(LOCAL_SCENE_MAP.values());
    }
}
