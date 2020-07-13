package com.game.gameserver.module.pet.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/23 9:38
 */
public class UserPet {
    /** 存储召唤物容器 */
    private final List<Pet> petList = new ArrayList<>();

    public boolean hasPet(int petConfigId){
        for(Pet pet:petList){
            if(pet.getPetConfig().getId()==petConfigId){
                return true;
            }
        }
        return false;
    }

    public void addPet(Pet pet){
        petList.add(pet);
    }
}
