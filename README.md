# gamedome

## 账户模块
LOGIN 参数（账户，密码）  功能：登录账户    
REGISTER 参数（账户，密码） 功能：注册账户    
LOGOUT 参数（无）  功能：退出登录    

## 角色模块  
LOGIN_ROLE 参数（角色Id) 功能：登录角色    
CREATE_ROLE 参数（角色名，职业Id) 功能：创建角色  
ROLE_LIST 参数（无）  功能 查看角色列表    
CAREER_LIST 参数（无） 功能：查看职业列表    
SHOW_ROLE 参数（无） 功能：展示当前角色信息    
LOGOUT_ROLE 参数（无） 功能：退出当前角色    

## 场景
SHOW_SCENE 参数（无） 功能：展示当前角色所在场景信息    
MOVE_SCENE 参数（场景Id） 功能：移动到别的场景    
AIO 参数（无） 功能：查看场景所有实体数据     
SCENE_LIST 参数(无) 功能：展示场景列表    
CHECK_SCENE 参数（场景Id) 根据Id查看场景信息    
TALK_NPC 参数（npcId） 与NPC交谈      

## 装备  
SHOW_EQUIP_BAR 参数（无） 功能：展示装备栏  
PUT_EQUIP 参数（道具位置） 功能：穿上装备  
TAKE_EQUIP 参数（装备位置） 功能：卸下装备  

## 背包  
SHOW_BACK_BAG  参数（无） 功能：展示当前背包  
MOVE_ITEM 参数（目标位置） 功能：移动道具  
DISCARD_ITEM 参数（丢弃道具） 功能：丢弃道具  
CLEAN_UP 参数（无） 功能：整理背包  

## 道具  
SHOW_ITEM  参数（背包类型，道具位置） 功能：战术道具信息  
USE_ITEM  参数（道具在背包的位置） 功能：使用道具  

## 技能  
SHOW_CAREER_SKILL  参数（无） 功能：展示当前职业技能  
SHOW_SKILL 参数（无）  功能：展示当前角色技能  
LEARN_SKILL 参数（技能Id) 功能：学习技能  
FORGET_SKILL 参数（技能Id) 功能：遗忘技能  
USE_SKILL 参数（目标Id，目标类型，技能Id) 功能：使用技能  

## 战斗  
ATTACK 参数（目标Id，目标类型，技能Id) 功能：使用技能攻击目标  
CHANGE_MODEL 参数（模式Id) 功能：切换PK模式  

## 副本  
SHOW_ALL_INSTANCE 参数（无） 功能：展示所有副本  
ENTRY_INSTANCE 参数（副本Id）功能：单人进入副本  
ENTRY_INSTANCE_BY_TEAM 参数（副本Id）功能：组队进入副本  
EXIT_INSTANCE 参数（无） 功能：退出当前副本  

## 商店
SHOW_SHOP_LIST 参数(无) 展示商店列表  
SHOW_SHOP 参数(商店Id) 展示商店   
SELL 参数（道具背包位置）出售物品   
BUY 参数（商店Id，商品Id，商品数量)  

## 聊天  
PRIVATE_CHAT 参数（目标Id，聊天内容） 功能：私聊  
LOCAL_CHAT 参数（聊天内容） 功能：本地聊天  
CHANNEL_CHAT 参数（频道Id,聊天内容） 功能：频道聊天  

## 邮箱  
SHOW_EMAIL_BOX 参数（无） 功能：展示邮箱    
SHOW_EMAIL 参数（邮件Id) 功能：展示邮件     
EXTRACT_ATTACH 参数（邮件Id）功能：提取附件    
DELETE_EMAIL 参数（邮件Id）功能：删除邮件    
SEND_EMAIL 参数（接收者Id，标题，内容，金币，附件） 功能：发送邮件    

## 组队 
CREATE_TEAM 参数（队伍名称） 功能：创建队伍    
SHOW_TEAM 参数（无） 功能：展示当前队伍    
SHOW_TEAM_LIST 参数（无） 功能：展示队伍列表    
APPLY_FOR_TEAM 参数（队伍Id) 功能：申请入队    
INVITE_TEAM_APPLY 参数（玩家Id） 功能：邀请玩家加入队伍      
PROCESS_TEAM_APPLY 参数（玩家Id，结果参数) 功能：处理玩家入队申请  
PROCESS_TEAM_INVITE 参数（队伍Id，结果参数） 功能：处理队伍邀请  
EXIT_TEAM 参数(无) 功能：退出角色当前队伍    
DISSOLVE_TEAM 参数（无） 功能：解散当前角色所在队伍    

## 交易  
SHOW_TRADE 参数（无） 功能：展示当前正在进行的交易    
INITIATE 参数（玩家Id）功能：申请交易    
REPLY_TRADE 参数（交易Id，结果） 功能：对申请交易的回复    
PUT_ITEM_TRADE 参数（背包中道具的位置） 功能：放入道具到交易栏    
PUT_GOLD_TRADE 参数（金币数量） 功能：放入金币到交易栏    
AFFIRM_TRADE 参数（无） 功能：确认交易栏  
CANCEL_TRADE 参数（无） 功能：取消交易    

## 拍卖  
SHOW_AUCTION_HOUSE 参数（无） 功能：展示拍卖行    
SHOW_ME_AUCTION 参数（无） 功能：展示自己上架的拍卖品    
PUSH_AUCTION 参数（模式，道具位置，道具数量，价格） 功能：上架拍卖品    
TAKE_AUCTION 参数（拍卖品Id）功能：下架拍卖品    
AUCTION 参数（拍卖品Id，价格） 功能：竞拍    
FIXED_PRICE 参数（拍卖品Id）功能：一口价    

## 公会  
CREATE_GUILD 参数（公会名称） 功能：创建公会    
SHOW_GUILD_LIST 参数（无） 功能：展示公会列表    
SHOW_GUILD 参数（无） 功能：展示自己当前的公会信息    
APPLY_FOR_GUILD 参数（公会Id）功能：申请加入公会    
PROCESS_GUILD_APPLY 参数（申请者ID，结果） 功能：处理公会申请信息    
APPOINT 参数（公会成员名称，职位Id）功能：授予职位    
DONATE_GOLDS 参数（金币数量） 功能：捐献金币  
EXIT_GUILD 参数(无) 功能：退出当前公会    
SHOW_GUILD_W 参数（无） 功能：展示公会仓库    
PUTIN_GUILD_W 参数（道具在背包中的位置） 功能：放入道具到公会仓库    
TAKEOUT_GUILD_W 参数（道具在仓库的位置） 功能：取出公会仓库道具     
CLEAR_UP_W 参数（无） 功能：整理公会仓库    

## 任务 
SHOW_ALL_TASK 参数（无） 功能：展示所有任务    
SHOW_RECEIVE_ABLE_TASK 参数（无） 功能：查看当前可接受任务    
SHOW_RECEIVE_TASK 参数（无）  功能：展示当前已经接受的任务    
ACCEPT_TASK 参数（任务Id）功能：接受任务    
CANCEL_TASK 参数（任务Id）功能：取消任务    
SUBMIT_TASK 参数（任务Id）功能：提交任务    

## 成就
SHOW_ACHIEVEMENT 参数（无）展示成就    
SUBMIT_ACHIEVEMENT 参数（成就Id）提交成就  


## 好友  
SHOW_FRIEND 参数（无） 功能： 展示好友列表    
APPLY_FOR_FRIEND 参数（角色Id）功能：好友申请    
PROCESS_FRIEND_APPLY 参数（角色名,结果） 功能处理好友申请    
REMOVE_FRIEND 参数（好友Id) 功能：删除好友    
CHANGE_FRIEND_TYPE 参数（好友Id,类型Id ） 功能：更改好友类型     
  
