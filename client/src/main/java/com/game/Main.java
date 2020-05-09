package com.game;

import com.game.page.GameFrame;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


/**
 * @author xuwenkang
 */
public class Main
{
    public static void main( String[] args ) throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.game");
        ctx.refresh();
        //System.in.read();
    }


}
