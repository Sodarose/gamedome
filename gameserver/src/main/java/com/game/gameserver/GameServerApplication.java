package com.game.gameserver;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.event.Listener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author xuewenkang
 */
@SpringBootApplication
public class GameServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServerApplication.class, args);
        Map<String, Object> listerObjectMap = ServerContext
                .getApplication().getBeansWithAnnotation(Listener.class);
        for (Map.Entry<String, Object> entry : listerObjectMap.entrySet()) {
            Class clazz = entry.getClass();
            Method[] method = clazz.getMethods();
        }
    }

}
