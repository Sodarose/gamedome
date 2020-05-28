package com.game.gameserver.module.bag.facade.impl;

import com.game.gameserver.module.bag.dao.BagMapper;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.bag.entity.Cell;
import com.game.gameserver.module.bag.facade.BagFacade;
import com.game.gameserver.module.bag.model.BagModel;
import com.game.gameserver.module.bag.model.CellModel;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.facade.ItemFacade;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.BagProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/27 9:59
 */
@Service
public class BagFacadeImpl implements BagFacade {

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private ItemFacade itemFacade;
    @Autowired
    private BagMapper bagMapper;

    @Override
    public Bag getBagByRoleId(Integer roleId,Integer type) {
        BagModel bagModel = bagMapper.getBagByRoleId(roleId,type);
        Bag bag = new Bag(bagModel.getId(),bagModel.getName(),bagModel.getType(),bagModel.getRoleId());
        List<Cell> cellList = getCellListByBagIdAndRoleId(bag.getId());
        bag.init(cellList);
        return bag;
    }

    /**
     * 根据背包Id 获取背包内道具数据
     * @param bagId
     * @return java.util.List<com.game.gameserver.module.bag.entity.Cell>
     */
    private List<Cell> getCellListByBagIdAndRoleId(Integer bagId) {
        List<CellModel> cellModels = bagMapper.getCellByBagId(bagId);
        List<Integer> itemIds = new ArrayList<>();
        for(CellModel cellModel:cellModels){
            itemIds.add(cellModel.getItemId());
        }
        Map<Integer, Item> itemMap = itemFacade.getItemMapByItemList(itemIds);
        List<Cell> cellList = new ArrayList<>();
        for(CellModel cellModel:cellModels){
            Cell cell = new Cell(cellModel);
            cell.putItem(itemMap.get(cellModel.getItemId()));
            cellList.add(cell);
        }
        return cellList;
    }

    @Override
    public BagProtocol.BagInfo openBag(Integer roleId, Integer bagId) {
        Player player = playerManager.getPlayer(roleId);
        if(player==null){
            return null;
        }
        Bag bag = player.getBag();
        bag.open();
        List<Cell> cells = bag.getNotNullCellList();
        BagProtocol.BagInfo.Builder bagBuilder = BagProtocol.BagInfo.newBuilder();
        bagBuilder.setBagId(bag.getId());
        bagBuilder.setBagName(bag.getName());
        for(Cell cell:cells){
            bagBuilder.addCellInfo(TransFromUtil.cellTransFromBafProtocolCellInfo(cell));
        }
        return bagBuilder.build();
    }

    @Override
    public BagProtocol.CloseBag closeBag(Integer roleId, Integer bagId) {
        return null;
    }

}
