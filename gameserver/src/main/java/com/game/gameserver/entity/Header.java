package com.game.gameserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuewenkang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Header {
    private Integer length;
    private Integer type;
}
