package com.game.gameserver.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuewenkang
 * 协议
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Header header;
    private byte[] data;

    public Message(int length,int type,byte[] data){
        this.header = new Header(length,type);
        this.data = data;
    }

}
