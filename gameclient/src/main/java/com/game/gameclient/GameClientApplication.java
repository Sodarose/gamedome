package com.game.gameclient;

import com.game.gameclient.client.GameClient;
import com.game.gameclient.view.ClientView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kangkang
 */
public class GameClientApplication {

    public static void main(String[] args) {
        ClientView clientView = new ClientView();
        clientView.repaint();
        GameClient gameClient = new GameClient();
        gameClient.run();
    }

}
