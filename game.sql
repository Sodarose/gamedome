/*
Navicat MySQL Data Transfer

Source Server         : game
Source Server Version : 80020
Source Host           : localhost:3306
Source Database       : game

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2020-05-13 22:04:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_map
-- ----------------------------
DROP TABLE IF EXISTS `game_map`;
CREATE TABLE `game_map` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '地图名称',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地图注释',
  `way` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地图路径出口',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of game_map
-- ----------------------------
INSERT INTO `game_map` VALUES ('1', '初始之地', '一切的开端', '2,');
INSERT INTO `game_map` VALUES ('2', '村庄', '宁静的村庄中 隐藏这不为人知的秘密', '1,3,4,');
INSERT INTO `game_map` VALUES ('3', '城堡', '阴森的城堡 城堡的主人 在等着勇者的到来', '2,');
INSERT INTO `game_map` VALUES ('4', '森林', '广阔茂密的森岭，传说里面存在着恶龙', '2,');

-- ----------------------------
-- Table structure for game_role
-- ----------------------------
DROP TABLE IF EXISTS `game_role`;
CREATE TABLE `game_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色名',
  `ph` int DEFAULT NULL COMMENT '生命值',
  `mp` int DEFAULT NULL COMMENT '法力值',
  `phy_attack` int DEFAULT NULL COMMENT '物理攻击力',
  `magic_attack` int DEFAULT NULL COMMENT '魔法攻击力',
  `phy_defense` int DEFAULT NULL COMMENT '物理防御',
  `magic_defense` int DEFAULT NULL COMMENT '魔法防疫',
  `map_id` int DEFAULT NULL COMMENT '地图ID',
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `game_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of game_role
-- ----------------------------
INSERT INTO `game_role` VALUES ('1', '霸王', '99999', '99999', '99999', '99999', '99999', '99999', '4', '1');
INSERT INTO `game_role` VALUES ('2', '刘邦', '88888', '88888', '88888', '88888', '88888', '88888', '4', '2');

-- ----------------------------
-- Table structure for game_user
-- ----------------------------
DROP TABLE IF EXISTS `game_user`;
CREATE TABLE `game_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`,`login_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of game_user
-- ----------------------------
INSERT INTO `game_user` VALUES ('1', '2601624788', 'qq3309887');
INSERT INTO `game_user` VALUES ('2', '15625591029', 'qq3309887');
