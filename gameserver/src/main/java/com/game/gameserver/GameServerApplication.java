package com.game.gameserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author xuewenkang
 */
@SpringBootApplication
@MapperScan(basePackages = "com.game.gameserver.game.mapper")
public class GameServerApplication {

    public static void main(String[] args)  {
        SpringApplication.run(GameServerApplication.class, args);
    }

}
