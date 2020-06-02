package com.game.gameserver.module.item.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.gameserver.module.item.model.EquipProperty;
import com.game.gameserver.module.item.model.ItemModel;
import com.game.gameserver.module.item.model.ItemProperty;
import com.game.gameserver.module.item.service.ItemService;
import com.game.protocol.ItemProtocol;
import lombok.Data;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * 物品通用属性
 *
 * @author kangkang
 */
@Data
public class Item {

    private ItemModel itemModel;
    private JSONObject property;

    public Item(ItemModel itemModel) {
        this.itemModel = itemModel;
        property = JSON.parseObject(itemModel.getProperty());
    }

    public ItemProtocol.ItemInfo getItemInfo(){
        ItemProtocol.ItemInfo.Builder builder = ItemProtocol.ItemInfo.newBuilder();

        return builder.build();
    }
}
