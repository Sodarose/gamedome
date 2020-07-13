package com.game.gameserver.module.guild.service;

import com.game.gameserver.module.guild.dao.GuildDbService;
import com.game.gameserver.module.guild.domain.GuildDomain;
import com.game.gameserver.module.guild.entity.Apply;
import com.game.gameserver.module.guild.entity.Guild;
import com.game.gameserver.module.guild.entity.Member;
import com.game.gameserver.module.guild.helper.GuildHelper;
import com.game.gameserver.module.guild.manager.GuildManager;
import com.game.gameserver.module.guild.type.GuildPermission;
import com.game.gameserver.module.guild.type.GuildPosition;
import com.game.gameserver.module.guild.type.PositionPermissionMapping;;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * 公会服务
 *
 * @author xuewenkang
 * @date 2020/7/3 9:40
 */
@Component
public class GuildService {

    private final static Logger logger = LoggerFactory.getLogger(GuildService.class);

    @Autowired
    private GuildManager guildManager;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GuildDbService guildDbService;

    /**
     * 加载公会
     *
     * @param
     * @return void
     */
    public void loadGuild() {
        logger.info("加载公会数据");
    }


    /**
     * 创建公户
     *
     * @param guildName 公会名称
     * @return com.game.protocol.GuildProtocol.CreateGuildRes
     */
    public void createGuild(Player player, String guildName) {
        // 角色是否已经加入公会
        if (player.getPlayerEntity().getGuildId() != null) {
            NotificationHelper.notifyPlayer(player,"您已经加入了一个公会，不允许创建公会哦");
        }
        // 创建公会信息
        Guild guild = Guild.valueOf(guildName);
        // 创建成员
        Member member = Member.valueOf(player.getPlayerEntity().getName(),
                GuildPosition.PRESIDENT, player.getPlayerEntity().getId());
        // 放入domain
        GuildDomain guildDomain = new GuildDomain(guild);
        guildDomain.setGuild(guild);
        guildDomain.getMemberMap().put(member.getName(), member);
        // 持久化数据

        // 放入缓存S
        guildManager.putGuildDomain(guildDomain.getGuild().getId(), guildDomain);
        // 设置公会Id
        player.getPlayerEntity().setGuildId(guild.getId());
        NotificationHelper.notifyPlayer(player,"公会创建成功");
    }

    /**
     * 查看公会列表
     *
     * @param
     * @return com.game.protocol.GuildProtocol.CheckGuildListRes
     */
    public void checkGuildList() {

    }

    /**
     * 查看自身公会
     *
     * @param player
     * @return void
     */
    public void checkGuild(Player player) {
        // 查看是否加入了公会
        if (player.getPlayerEntity().getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "没有加入公会");
            return;
        }
        GuildDomain guildDomain = guildManager.getGuildDomain(player.getPlayerEntity().getGuildId());
        // 公会不存在 数据异常
        if (guildDomain == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        // 返回公会数据
        NotificationHelper.notifyPlayer(player, GuildHelper.buildGuildDomainInfo(guildDomain));
    }

    /**
     * 申请加入公会
     *
     * @param player
     * @param guildId
     * @return void
     */
    public void applyGuild(Player player, long guildId) {
        // 是否已经加入了公会
        if (player.getPlayerEntity().getGuildId() != null) {
            NotificationHelper.notifyPlayer(player, "你已经加入了公会");
            return;
        }
        // 公会是否存在
        GuildDomain guildDomain = guildManager.getGuildDomain(guildId);
        if (guildDomain == null) {
            NotificationHelper.notifyPlayer(player, "公会不存在");
            return;
        }
        // 生成申请单 放入申请表中
        Apply apply = new Apply(player.getPlayerEntity().getName(), player.getPlayerEntity().getId());
        guildDomain.getApplyMap().put(apply.getName(), apply);
        NotificationHelper.notifyPlayer(player, "已经提交申请");
    }

    /**
     * 处理入会申请
     *
     * @param player
     * @param applyName
     * @return void
     */
    public void processApplyInfo(Player player, String applyName, boolean agree) {
        // 是否已经加入了公会
        if (player.getPlayerEntity().getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有进入公会");
            return;
        }
        // 公会是否存在
        GuildDomain guildDomain = guildManager.getGuildDomain(player.getPlayerEntity().getGuildId());
        if (guildDomain == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        // 获取玩家职位
        Member member = guildDomain.getMemberMap().get(player.getPlayerEntity().getName());
        if (member == null) {
            NotificationHelper.notifyPlayer(player, "获取职位信息异常");
            return;
        }
        // 获取权限 判断是否拥有决策申请人权限
        List<Integer> perms = PositionPermissionMapping.POSITION_PERMISSION_MAP.get(member.getPosition());
        if (!perms.contains(GuildPermission.PROCESS_APPLY)) {
            NotificationHelper.notifyPlayer(player, "你没有处理玩家申请的权限");
            return;
        }
        // 找到申请表
        Apply apply = guildDomain.getApplyMap().get(applyName);
        if (apply == null) {
            NotificationHelper.notifyPlayer(player, "该玩家没有申请加入公会");
            return;
        }
        // 同意该玩家入会
        Player applyPlayer = playerService.getPlayer(apply.getPlayerId());
        if (agree) {
            applyPlayer.getPlayerEntity().setGuildId(guildDomain.getGuild().getId());
            // 添加新成员
            Member newMember = Member.valueOf(applyPlayer.getPlayerEntity().getName(),
                    GuildPosition.MEMBER, applyPlayer.getPlayerEntity().getId());
            guildDomain.getMemberMap().put(newMember.getName(), newMember);
            // 删除申请单
            guildDomain.getApplyMap().remove(apply.getName());
            // 通知用户
            NotificationHelper.notifyPlayer(player, "已经同意该玩家加入公会");
            NotificationHelper.notifyPlayer(applyPlayer, "你已经加入该公会");
            // 加入公会事件

        } else {
            guildDomain.getApplyMap().remove(apply.getName());
            // 通知用户
            NotificationHelper.notifyPlayer(player, "已拒绝该玩家");
            NotificationHelper.notifyPlayer(applyPlayer, "拒绝该玩家");
        }
        // 更新数据库
        guildDbService.update(guildDomain);
    }

    /**
     *
     * @param player
     * @param memberName
     * @param position
     * @return void
     */
    public void appointPosition(Player player, String memberName, int position) {
        // 是否已经加入了公会
        if (player.getPlayerEntity().getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有进入公会");
            return;
        }
        // 公会是否存在
        GuildDomain guildDomain = guildManager.getGuildDomain(player.getPlayerEntity().getGuildId());
        if (guildDomain == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        // 上锁
        // 获取玩家职位
        Member member = guildDomain.getMemberMap().get(player.getPlayerEntity().getName());
        if (member == null) {
            NotificationHelper.notifyPlayer(player, "获取职位信息异常");
            return;
        }
        // 判断决策权限
        if (position >= member.getPosition()) {
            NotificationHelper.notifyPlayer(player, "你不能授予大于自身等级或者同级的职位");
            return;
        }
        // 获取角色职位表
        Member targetMember = guildDomain.getMemberMap().get(memberName);
        targetMember.setPosition(position);
        Player targetPlayer = playerService.getPlayer(targetMember.getPlayerId());
        // 通知
        NotificationHelper.notifyPlayer(player, "授予职位成功");
        NotificationHelper.notifyPlayer(targetPlayer, "你被授予" + position + "职位");
        // 更新数据库
        guildDbService.update(guildDomain);
    }

    /**
     * 捐献金币
     *
     * @param player
     * @param golds
     * @return void
     */
    public void donateGold(Player player, int golds) {
        // 是否已经加入了公会
        if (player.getPlayerEntity().getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有进入公会");
            return;
        }
        // 公会是否存在
        GuildDomain guildDomain = guildManager.getGuildDomain(player.getPlayerEntity().getGuildId());
        if (guildDomain == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        // 读写锁
        Lock lock = guildDomain.getWriteLock();
        lock.lock();
        try {
            // 判断金币是否足够
            if (player.getPlayerEntity().getGolds() < golds) {
                NotificationHelper.notifyPlayer(player, "金币不足");
                return;
            }
            // 扣除用户金币

            player.getPlayerEntity().setGolds(player.getPlayerEntity().getGolds()-golds);
            // 公会增加金币
            guildDomain.getGuild().addGolds(golds);
        }finally {
            lock.unlock();
        }
        // 发送消息
        NotificationHelper.notifyPlayer(player, "成功捐献"+golds+"金币");
        NotificationHelper.syncPlayer(player);
        // 更新数据库
        guildDbService.update(guildDomain);
    }

}
