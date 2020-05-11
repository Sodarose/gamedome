package com.game.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xuewenkang
 * 消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
   private Integer length;
   private Short cmd;
   private byte[] data;
}
