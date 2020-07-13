package com.game.gameserver.module.guild.helper;

import com.game.gameserver.module.guild.domain.GuildDomain;

/**
 * @author xuewenkang
 * @date 2020/7/7 20:51
 */
public class GuildHelper {

    /**
     * 构建公会信息
     *
     * @param guildDomain
     * @return java.lang.String
     */
    public static String buildGuildDomainInfo(GuildDomain guildDomain) {
        StringBuilder builder = new StringBuilder("公会信息:").append("\n");
        // 基本信息
        builder.append("公会基本信息:").append("\n");
        builder.append("公会ID:").append(guildDomain.getGuild().getId()).append("\n");
        builder.append("公会名:").append(guildDomain.getGuild().getName()).append("\n");
        builder.append("公会等级:").append(guildDomain.getGuild().getLevel()).append("\n");
        builder.append("成员:").append(guildDomain.getMemberMap().size()).append("/").append(guildDomain.getGuild().getCapacity())
                .append("\n");
        builder.append("金币贡献值:").append(guildDomain.getGuild().getGolds()).append("\n");
        builder.append("公告:").append(guildDomain.getGuild().getAnnouncement()).append("\n");

        // 成员信息
        builder.append("公会成员:").append("\n");
        guildDomain.getMemberMap().values().forEach(member -> {
            builder.append("姓名:").append(member.getName()).append("(").append(member.getPosition())
                    .append(")").append("\n");
        });

        // 申请入会人
        builder.append("申请入会名单:").append("\n");
        guildDomain.getApplyMap().keySet().forEach(name -> {
            builder.append(name).append("\t");
        });
        return builder.toString();
    }
}
