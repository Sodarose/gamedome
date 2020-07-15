package com.game.gameserver.module.guild.service;

import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.event.GuildEvent;
import com.game.gameserver.module.guild.dao.GuildDbService;
import com.game.gameserver.module.guild.dao.GuildWarehouseDbService;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.guild.model.Applicant;
import com.game.gameserver.module.guild.model.GuildWarehouse;
import com.game.gameserver.module.guild.model.Member;
import com.game.gameserver.module.guild.helper.GuildHelper;
import com.game.gameserver.module.guild.manager.GuildManager;
import com.game.gameserver.module.guild.type.GuildPermission;
import com.game.gameserver.module.guild.type.GuildPosition;
import com.game.gameserver.module.guild.type.PositionPermissionMapping;;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.util.GameUUID;
import com.game.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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

    @Autowired
    private GuildWarehouseDbService guildWarehouseDbService;

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
            return;
        }
        // 判断公会名是否已经存在
        if(guildDbService.count(guildName)!=0){
            NotificationHelper.notifyPlayer(player,"该工会名称已经被使用");
            return;
        }

        // 新建一个公会 创建公会信息
        Guild guild = new Guild();
        guild.setId(GameUUID.getInstance().generate());
        guild.setName(guildName);
        guild.setLevel(1);
        guild.setExpr(0);
        guild.setCapacity(200);
        guild.setGolds(0);
        guild.setAnnouncement("");
        guild.setMemberMap(new ConcurrentHashMap<>());
        guild.setApplicantMap(new ConcurrentHashMap<>());

        // 创建公会仓库
        GuildWarehouse guildWarehouse = new GuildWarehouse(guild.getId(),64);
        guild.setGuildWarehouse(guildWarehouse);

        // 生成自身成员数据
        Member member = new Member();
        member.setName(player.getName());
        member.setPlayerId(player.getId());
        member.setPosition(GuildPosition.PRESIDENT);
        guild.getMemberMap().put(member.getName(),member);

        // 放入缓存
        guildManager.putGuild(guild.getId(),guild);
        player.setGuildId(guild.getId());

        // 持久化数据
        guildDbService.insertAsync(GuildHelper.transFromGuildEntity(guild));
        guildWarehouseDbService.insertAsync(GuildHelper.transFromGuildWarehouseEntity(guildWarehouse));
        NotificationHelper.notifyPlayer(player,"创建公会成功");
    }

    /**
     * 查看公会列表
     *
     * @param
     * @return com.game.protocol.GuildProtocol.CheckGuildListRes
     */
    public void showGuildList(Player player) {
        List<Guild> guilds = guildManager.getAllGuild();
        NotificationHelper.notifyPlayer(player,GuildHelper.buildGuildList(guilds));
    }

    /**
     * 查看自身公会
     *
     * @param player
     * @return void
     */
    public void showGuild(Player player) {
        // 查看是否加入了公会
        if (player.getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "没有加入公会");
            return;
        }
        Guild guild = guildManager.getGuild(player.getPlayerEntity().getGuildId());
        // 公会不存在 数据异常
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        // 返回公会数据
        NotificationHelper.notifyPlayer(player, GuildHelper.buildGuildMsg(guild));
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
        if (player.getGuildId() != null) {
            NotificationHelper.notifyPlayer(player, "你已经加入了公会");
            return;
        }
        // 公会是否存在
        Guild guild = guildManager.getGuild(guildId);
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "公会不存在");
            return;
        }
        Lock lock = guild.getWriteLock();
        try {
            if (guild.getMemberMap().size() == guild.getCapacity()) {
                NotificationHelper.notifyPlayer(player, "公会人数已经达到上限");
                return;
            }
            // 生成申请单 放入申请表中
            Applicant applicant = new Applicant(player.getName(), player.getId());
            guild.getApplicantMap().put(applicant.getName(), applicant);
            NotificationHelper.notifyPlayer(player, "已经提交申请");
            // 异步更新数据
            guildDbService.updateAsync(GuildHelper.transFromGuildEntity(guild));
        }finally {
            lock.unlock();
        }
    }

    /**
     * 处理申请者
     *
     * @param player
     * @param applicantName
     * @return void
     */
    public void processApplicant(Player player, String applicantName, int agree) {
        // 是否已经加入了公会
        if (player.getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有进入公会");
            return;
        }
        // 公会是否存在
        Guild guild = guildManager.getGuild(player.getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        Lock lock = guild.getWriteLock();
        lock.lock();
        try {
            // 获取玩家职位
            Member member = guild.getMemberMap().get(player.getPlayerEntity().getName());
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
            Applicant applicant = guild.getApplicantMap().get(applicantName);
            if (applicant == null) {
                NotificationHelper.notifyPlayer(player, "该玩家没有申请加入公会");
                return;
            }
            // 同意该玩家入会
            Player applyPlayer = playerService.getPlayer(applicant.getPlayerId());
            if (agree == 1) {
                // 是否达到上限
                if (guild.getMemberMap().size() == guild.getCapacity()) {
                    NotificationHelper.notifyPlayer(player, "公会人数已经达到上限");
                    return;
                }
                applyPlayer.getPlayerEntity().setGuildId(guild.getId());
                // 添加新成员
                Member newMember = new Member(applyPlayer.getPlayerEntity().getName(),
                        GuildPosition.MEMBER, applyPlayer.getPlayerEntity().getId());
                guild.getMemberMap().put(newMember.getName(), newMember);
                // 删除申请单
                guild.getApplicantMap().remove(applicant.getName());
                // 通知用户
                NotificationHelper.notifyGuild(guild, MessageFormat.format("玩家{0}加入公会", player.getName()));
                NotificationHelper.notifyPlayer(applyPlayer, "你已经加入该公会");
                // 抛出加入公会事件
                GuildEvent guildEvent = new GuildEvent(applyPlayer,guild);
                EventBus.EVENT_BUS.fire(guildEvent);

            } else if (agree == 0) {
                // 删除申请表里面的信息
                guild.getApplicantMap().remove(applicant.getName());
                // 通知用户
                NotificationHelper.notifyPlayer(player, "已拒绝该玩家");
                NotificationHelper.notifyPlayer(applyPlayer, "拒绝该玩家");
            }
            // 异步更新更新数据库
            guildDbService.updateAsync(GuildHelper.transFromGuildEntity(guild));
        }finally {
            lock.unlock();
        }
    }

    /**
     * 授予职位
     *
     * @param player
     * @param memberName
     * @param position
     * @return void
     */
    public void appointPosition(Player player, String memberName, int position) {
        // 是否已经加入了公会
        if (player.getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有进入公会");
            return;
        }
        // 公会是否存在
        Guild guild = guildManager.getGuild(player.getPlayerEntity().getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        Lock lock = guild.getWriteLock();
        lock.lock();
        try {
            // 获取玩家职位
            Member member = guild.getMemberMap().get(player.getPlayerEntity().getName());
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
            Member targetMember = guild.getMemberMap().get(memberName);
            if (targetMember == null) {
                NotificationHelper.notifyPlayer(player, "公会中没有该玩家");
                return;
            }
            targetMember.setPosition(position);
            Player targetPlayer = playerService.getPlayer(targetMember.getPlayerId());
            // 通知
            NotificationHelper.notifyPlayer(player, "授予职位成功");
            NotificationHelper.notifyPlayer(targetPlayer, "你被授予" + position + "职位");
            // 异步更新数据库
            guildDbService.updateAsync(GuildHelper.transFromGuildEntity(guild));
        }finally {
            lock.unlock();
        }
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
        Guild guild = guildManager.getGuild(player.getPlayerEntity().getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        // 读写锁
        Lock lock = guild.getWriteLock();
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
            guild.addGolds(golds);
        }finally {
            lock.unlock();
        }
        // 发送消息
        NotificationHelper.notifyPlayer(player, "成功捐献"+golds+"金币");
        NotificationHelper.notifyGuild(guild,MessageFormat.format("玩家{0}捐献{1}金币",player.getName(),golds));
        NotificationHelper.syncPlayer(player);
        // 更新数据库
        guildDbService.updateAsync(GuildHelper.transFromGuildEntity(guild));
    }

    public Guild getGuildDomain(long guildId){
        return guildManager.getGuild(guildId);
    }

    public void exitGuild(Player player){
        // 是否已经加入了公会
        if (player.getPlayerEntity().getGuildId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有进入公会");
            return;
        }
        // 公会是否存在
        Guild guild = guildManager.getGuild(player.getPlayerEntity().getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "公会数据异常");
            return;
        }
        // 读写锁
        Lock lock = guild.getWriteLock();
        lock.lock();
        try{
            Member member =  guild.getMemberMap().remove(player.getName());
            if(member==null){
                return;
            }
            player.setGuildId(null);
            NotificationHelper.notifyGuild(guild,MessageFormat.format("玩家{0}退出公会"
                    ,player.getName()));
            NotificationHelper.notifyPlayer(player,"退出公会");
            if(!guild.getMemberMap().isEmpty()){
                // 更新数据库
                guildDbService.updateAsync(GuildHelper.transFromGuildEntity(guild));
            }else{
                // 移除该公会
                guildManager.remove(guild.getId());
                guildWarehouseDbService.delete(guild.getId());
                guildDbService.deleteAsync(guild.getId());
            }
        }finally {
            lock.unlock();
        }
    }

}
