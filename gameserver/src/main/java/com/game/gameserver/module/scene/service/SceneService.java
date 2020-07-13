package com.game.gameserver.module.scene.service.impl;

import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.SceneProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/6/17 17:13
 */
@Service
public class SceneServiceImpl implements SceneService {

    @Autowired
    private SceneManager sceneManager;

    /**
     * 返回场景列表
     *
     * @return com.game.protocol.SceneProtocol.QuerySceneListRes
     */
    @Override
    public SceneProtocol.QuerySceneListRes querySceneList() {
        return ProtocolFactory.createQuerySceneListRes(sceneManager.getSceneList());
    }
}
