package com.game.protocol;

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
