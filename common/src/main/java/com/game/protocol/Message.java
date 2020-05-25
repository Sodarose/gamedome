package com.game.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 消息
 * @author xuewenkang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
   private Integer length;
   private Short module;
   private Short cmd;
   private byte[] data;
}
