package com.game.gameclient.cmd;

import com.game.gameclient.type.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import sun.security.pkcs11.Secmod;

/**
 * @author xuewenkang
 * @date 2020/7/10 19:05
 */
public enum Cmd {
    /** 登录账户 */
    LOGIN(ModuleKey.USER_MODULE, UserCmd.LOGIN),
    /** 注册*/
    REGISTER(ModuleKey.USER_MODULE, UserCmd.REGISTER),
    /** 注销 */
    LOGOUT(ModuleKey.USER_MODULE, UserCmd.LOGOUT),

    /** 角色 */
    /** 登录角色*/
    LOGIN_ROLE(ModuleKey.PLAYER_MODULE, PlayerCmd.LOGIN),
    /** 注册角色 */
    CREATE_ROLE(ModuleKey.PLAYER_MODULE,PlayerCmd.CREATE),
    /** 角色列表 */
    ROLE_LIST(ModuleKey.PLAYER_MODULE,PlayerCmd.ROLE_LIST),
    /** 职业列表*/
    CAREER_LIST(ModuleKey.PLAYER_MODULE,PlayerCmd.CAREER_LIST),
    /** 展示角色*/
    SHOW_ROLE(ModuleKey.PLAYER_MODULE,PlayerCmd.SHOW_PLAYER),
    /** 退出角色*/
    LOGOUT_ROLE(ModuleKey.PLAYER_MODULE,PlayerCmd.LOGOUT),

    /** 场景*/
    /** 展示角色当前场景*/
    SHOW_SCENE(ModuleKey.SCENE_MODULE, SceneCmd.SHOW_SCENE),
    /** 移动场景 */
    MOVE_SCENE(ModuleKey.SCENE_MODULE, SceneCmd.MOVE_SCENE),
    /** AIO*/
    AIO(ModuleKey.SCENE_MODULE,SceneCmd.AIO),
    /** 场景列表*/
    SCENE_LIST(ModuleKey.SCENE_MODULE, SceneCmd.SCENE_LIST),
    /** 查看场景*/
    CHECK_SCENE(ModuleKey.SCENE_MODULE,SceneCmd.CHECK_SCENE),
    /** 与NPC交谈 暂时放入场景模块 */
    TALK_NPC(ModuleKey.SCENE_MODULE,SceneCmd.TALK_NPC),

    /** 装备 */
    /** 展示装备栏 */
    SHOW_EQUIP_BAR(ModuleKey.EQUIP_MODULE, EquipCmd.SHOW_EQUIP_BAR),
    /** 穿上装备*/
    PUT_EQUIP(ModuleKey.EQUIP_MODULE,EquipCmd.PUT_EQUIP),
    /** 卸下装备*/
    TAKE_EQUIP(ModuleKey.EQUIP_MODULE,EquipCmd.TAKE_EQUIP),

    /** 背包 */
    SHOW_BACK_BAG(ModuleKey.BACK_BAG_MODULE,BackBagCmd.SHOW_BACK_BAG),
    /** 移动道具*/
    MOVE_ITEM(ModuleKey.BACK_BAG_MODULE,BackBagCmd.MOVE_ITEM),
    /** 丢弃道具*/
    DISCARD_ITEM(ModuleKey.BACK_BAG_MODULE,BackBagCmd.DISCARD_ITEM),
    /** 整理背包*/
    CLEAR_UP_BAG(ModuleKey.BACK_BAG_MODULE,BackBagCmd.CLEAN_UP),

    /** 道具 */
    /** 展示道具*/
    SHOW_ITEM(ModuleKey.ITEM_MODULE,ItemCmd.SHOW_ITEM),
    /** 使用道具 */
    USE_ITEM(ModuleKey.ITEM_MODULE,ItemCmd.USE_ITEM),

    /** 技能 */

    /** 战斗 */

    /** 副本 */

    /** 商店*/

    /** 聊天*/

    /** 邮件*/
    /** 展示邮箱 */
    SHOW_EMAIL_BOX(ModuleKey.EMAIL_MODULE,EmailCmd.SHOW_EMAIL_BOX),
    /** 展示邮件 */
    SHOW_EMAIL(ModuleKey.EMAIL_MODULE,EmailCmd.SHOW_EMAIL),
    /** 提取附件 */
    EXTRACT_ATTACH(ModuleKey.EMAIL_MODULE,EmailCmd.EXTRACT_ATTACH),
    /** 删除邮件 */
    DELETE_EMAIL(ModuleKey.EMAIL_MODULE,EmailCmd.DELETE_EMAIL),
    /** 发送邮件*/
    SEND_EMAIL(ModuleKey.EMAIL_MODULE,EmailCmd.SEND_EMAIL),

    /** 组队 */
    /** 创建队伍 */
    CREATE_TEAM(ModuleKey.TEAM_MODULE,TeamCmd.CREATE_TEAM),
    /** 展示队伍 */
    SHOW_TEAM(ModuleKey.TEAM_MODULE,TeamCmd.SHOW_TEAM),
    /** 展示队伍列表 */
    SHOW_TEAM_LIST(ModuleKey.TEAM_MODULE,TeamCmd.SHOW_TEAM_LIST),
    /** 申请组队 */
    APPLY_FOR_TEAM(ModuleKey.TEAM_MODULE,TeamCmd.APPLY_FOR_TEAM),
    /** 邀请组队 */
    INVITE_TEAM(ModuleKey.TEAM_MODULE,TeamCmd.INVITE_TEAM),
    /** 处理申请 */
    PROCESS_APPLY(ModuleKey.TEAM_MODULE,TeamCmd.PROCESS_APPLY),
    /** 处理邀请 */
    PROCESS_INVITE(ModuleKey.TEAM_MODULE,TeamCmd.PROCESS_INVITE),
    /** 退出队伍 */
    EXIT_TEAM(ModuleKey.TEAM_MODULE,TeamCmd.EXIT_TEAM),
    /** 解散队伍 */
    DISSOLVE_TEAM(ModuleKey.TEAM_MODULE,TeamCmd.DISSOLVE_TEAM),

    /** 交易*/
    /** 显示当前交易 */
    SHOW_TRADE(ModuleKey.TRADE_MODULE,TradeCmd.SHOW_TRADE),
    /** 申请交易 */
    INITIATE(ModuleKey.TRADE_MODULE,TradeCmd.INITIATE),
    /** 回复交易 */
    REPLY_TRADE(ModuleKey.TRADE_MODULE,TradeCmd.REPLY_TRADE),
    /** 放入道具 */
    PUT_ITEM_TRADE(ModuleKey.TRADE_MODULE,TradeCmd.PUT_ITEM_TRADE),
    /** 放入金币 */
    PUT_GOLD_TRADE(ModuleKey.TRADE_MODULE,TradeCmd.PUT_GOLD_TRADE),
    /** 确认当前交易 */
    AFFIRM_TRADE(ModuleKey.TRADE_MODULE,TradeCmd.AFFIRM_TRADE),
    /** 取消当前交易 */
    CANCEL_TRADE(ModuleKey.TRADE_MODULE,TradeCmd.CANCEL_TRADE),

    /** 拍卖*/
    /** 显示拍卖信息 */
    SHOW_AUCTION_HOUSE(ModuleKey.AUCTION_MODULE,AuctionCmd.SHOW_AUCTION_HOUSE),
    /** 显示拍卖信息 */
    SHOW_ME_AUCTION(ModuleKey.AUCTION_MODULE,AuctionCmd.SHOW_ME_AUCTION),
    /** 上架 */
    PUSH_AUCTION(ModuleKey.AUCTION_MODULE,AuctionCmd.PUSH_AUCTION),
    /** 下架 */
    TAKE_AUCTION(ModuleKey.AUCTION_MODULE,AuctionCmd.TAKE_AUCTION),
    /** 竞拍 */
    AUCTION(ModuleKey.AUCTION_MODULE,AuctionCmd.AUCTION),
    /** 一口价 */
    FIXED_PRICE(ModuleKey.AUCTION_MODULE,AuctionCmd.FIXED_PRICE);

    /** 公会*/

    /** 任务*/

    /** 成就*/


    private Integer module;
    private Integer cmd;

    Cmd(int module, int cmd){
        this.module = module;
        this.cmd = cmd;
    }


    public Integer getCmd() {
        return cmd;
    }

    public Integer getModule() {
        return module;
    }


    @Override
    public String toString() {
        return "命令名:" +
                name() +
                "\t" +
                "Module:" +
                module +
                "\t" +
                "CmdId" +
                cmd;
    }
}
