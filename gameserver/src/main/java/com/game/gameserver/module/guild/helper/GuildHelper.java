package com.game.gameserver.module.guild.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.guild.entity.GuildEntity;
import com.game.gameserver.module.guild.entity.GuildWarehouseEntity;
import com.game.gameserver.module.guild.model.Applicant;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.guild.model.GuildWarehouse;
import com.game.gameserver.module.guild.model.Member;
import com.game.gameserver.module.item.model.Item;

import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/7 20:51
 */
public class GuildHelper {

    public static String buildGuildList(List<Guild> guildList){
        StringBuilder sb = new StringBuilder("公会列表:");
        guildList.forEach(guild -> {
            sb.append("id:").append(guild.getId()).append("\n");
            sb.append("name:").append(guild.getName()).append("\n");
            sb.append("capacity:").append(guild.getMemberMap().size()).append("/")
                    .append(guild.getCapacity()).append("\n");
            sb.append("announcement:").append(guild.getAnnouncement()).append("\n");
            sb.append("\n");
        });
        return sb.toString();
    }

    /**
     * 构建公会信息
     *
     * @param guild
     * @return java.lang.String
     */
    public static String buildGuildMsg(Guild guild) {
        StringBuilder builder = new StringBuilder("公会信息:").append("\n");
        // 基本信息
        builder.append("公会基本信息:").append("\n");
        builder.append("公会ID:").append(guild.getId()).append("\n");
        builder.append("公会名:").append(guild.getName()).append("\n");
        builder.append("公会等级:").append(guild.getLevel()).append("\n");
        builder.append("成员:").append(guild.getMemberMap().size()).append("/").append(guild.getCapacity())
                .append("\n");
        builder.append("金币贡献值:").append(guild.getGolds()).append("\n");
        builder.append("公告:").append(guild.getAnnouncement()).append("\n");

        // 成员信息
        builder.append("公会成员:").append("\n");
        guild.getMemberMap().values().forEach(member -> {
            builder.append("姓名:").append(member.getName()).append("(").append(member.getPosition())
                    .append(")").append("\n");
        });

        // 申请入会人
        builder.append("申请入会名单:").append("\n");
        guild.getApplicantMap().keySet().forEach(name -> {
            builder.append(name).append("\t");
        });
        return builder.toString();
    }


    public static String buildGuildWarehouse(GuildWarehouse guildWarehouse){
        StringBuilder sb = new StringBuilder("仓库信息:");
        for(int i=0;i<guildWarehouse.getCapacity();i++){
            Item item = guildWarehouse.getItemMap().get(i);
            sb.append("[").append(buildItemMsg(item)).append("]").append("\t");
            if(i!=0&&(i+1)%8==0){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static String buildItemMsg(Item item){
        StringBuilder sb = new StringBuilder();
        if(item==null){
            return "空";
        }
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(item.getItemConfigId());
        sb.append(itemConfig.getName()).append("(").append(item.getNum()).append(")");
        return sb.toString();
    }

    public static GuildEntity transFromGuildEntity(Guild guild){
        GuildEntity guildEntity = new GuildEntity(guild);
        // 序列化 成员
        String members = JSON.toJSONString(guild.getMemberMap());
        String applicants = JSON.toJSONString(guild.getApplicantMap());
        guildEntity.setMembers(members);
        guildEntity.setApplicants(applicants);
        return guildEntity;
    }

    public static Guild transFromGuild(GuildEntity guildEntity){
        Guild guild = new Guild(guildEntity);
        // 解析JSON 获得成员表
        Map<String, Member> itemMap = JSON.parseObject(guildEntity.getMembers(),
                new TypeReference<Map<String,Member>>(){});
        guild.getMemberMap().putAll(itemMap);

        // 解析JSON 获得申请表
        Map<String, Applicant> applicantMap = JSON.parseObject(guildEntity.getApplicants(),
                new TypeReference<Map<String,Applicant>>(){});
        guild.getApplicantMap().putAll(applicantMap);
        return guild;
    }

    public static GuildWarehouse transFromGuildWarehouse(GuildWarehouseEntity guildWarehouseEntity){
        GuildWarehouse guildWarehouse = new GuildWarehouse(guildWarehouseEntity);
        // 解析JSON 获得道具表
        Map<Integer, Item> itemMap = JSON.parseObject(guildWarehouseEntity.getItems(),
                new TypeReference<Map<Integer,Item>>(){});
        guildWarehouse.getItemMap().putAll(itemMap);
        return guildWarehouse;
    }

    public static GuildWarehouseEntity transFromGuildWarehouseEntity(GuildWarehouse guildWarehouse){
        GuildWarehouseEntity guildWarehouseEntity = new GuildWarehouseEntity(guildWarehouse);
        // 序列化道具表
        String items = JSON.toJSONString(guildWarehouse.getItemMap());
        guildWarehouseEntity.setItems(items);
        return guildWarehouseEntity;
    }
}
