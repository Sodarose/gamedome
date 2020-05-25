/*
Navicat MySQL Data Transfer

Source Server         : game
Source Server Version : 80020
Source Host           : localhost:3306
Source Database       : game

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2020-05-25 20:04:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_bag
-- ----------------------------
DROP TABLE IF EXISTS `game_bag`;
CREATE TABLE `game_bag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemId` int NOT NULL COMMENT '物品Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of game_bag
-- ----------------------------

-- ----------------------------
-- Table structure for game_equip
-- ----------------------------
DROP TABLE IF EXISTS `game_equip`;
CREATE TABLE `game_equip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `equipId` int NOT NULL COMMENT '装备ID',
  `durability` int NOT NULL COMMENT '当前耐久度',
  `equipment` int NOT NULL COMMENT '是否穿上',
  `itemId` int NOT NULL COMMENT '装备对应的Item表',
  PRIMARY KEY (`id`),
  KEY `equid_item` (`itemId`),
  CONSTRAINT `equid_item` FOREIGN KEY (`itemId`) REFERENCES `game_item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of game_equip
-- ----------------------------
INSERT INTO `game_equip` VALUES ('1', '3', '45', '1', '3');
INSERT INTO `game_equip` VALUES ('2', '4', '45', '1', '4');
INSERT INTO `game_equip` VALUES ('3', '5', '45', '1', '5');
INSERT INTO `game_equip` VALUES ('4', '6', '45', '1', '6');
INSERT INTO `game_equip` VALUES ('5', '7', '45', '1', '7');
INSERT INTO `game_equip` VALUES ('6', '8', '45', '1', '8');
INSERT INTO `game_equip` VALUES ('7', '9', '45', '1', '9');
INSERT INTO `game_equip` VALUES ('8', '10', '45', '1', '10');
INSERT INTO `game_equip` VALUES ('9', '11', '45', '1', '11');
INSERT INTO `game_equip` VALUES ('10', '12', '45', '1', '12');
INSERT INTO `game_equip` VALUES ('11', '13', '45', '1', '13');
INSERT INTO `game_equip` VALUES ('12', '14', '45', '1', '14');
INSERT INTO `game_equip` VALUES ('13', '15', '45', '1', '15');
INSERT INTO `game_equip` VALUES ('14', '16', '45', '0', '16');

-- ----------------------------
-- Table structure for game_item
-- ----------------------------
DROP TABLE IF EXISTS `game_item`;
CREATE TABLE `game_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemId` int NOT NULL COMMENT '道具ID',
  `itemType` int NOT NULL COMMENT '装备类型',
  `count` int NOT NULL COMMENT '数量',
  `userId` int NOT NULL COMMENT '拥有者',
  PRIMARY KEY (`id`),
  KEY `item_userId` (`userId`),
  CONSTRAINT `item_userId` FOREIGN KEY (`userId`) REFERENCES `game_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of game_item
-- ----------------------------
INSERT INTO `game_item` VALUES ('1', '1', '1', '15', '1');
INSERT INTO `game_item` VALUES ('2', '2', '1', '70', '1');
INSERT INTO `game_item` VALUES ('3', '3', '2', '1', '1');
INSERT INTO `game_item` VALUES ('4', '4', '2', '1', '1');
INSERT INTO `game_item` VALUES ('5', '5', '2', '1', '1');
INSERT INTO `game_item` VALUES ('6', '6', '2', '1', '1');
INSERT INTO `game_item` VALUES ('7', '7', '2', '1', '1');
INSERT INTO `game_item` VALUES ('8', '8', '2', '1', '1');
INSERT INTO `game_item` VALUES ('9', '9', '2', '1', '1');
INSERT INTO `game_item` VALUES ('10', '10', '2', '1', '1');
INSERT INTO `game_item` VALUES ('11', '11', '2', '1', '1');
INSERT INTO `game_item` VALUES ('12', '12', '2', '1', '1');
INSERT INTO `game_item` VALUES ('13', '13', '2', '1', '1');
INSERT INTO `game_item` VALUES ('14', '14', '2', '1', '1');
INSERT INTO `game_item` VALUES ('15', '15', '2', '1', '1');
INSERT INTO `game_item` VALUES ('16', '3', '2', '1', '1');

-- ----------------------------
-- Table structure for game_role
-- ----------------------------
DROP TABLE IF EXISTS `game_role`;
CREATE TABLE `game_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色名',
  `level` int DEFAULT NULL COMMENT '等级',
  `career` int DEFAULT NULL COMMENT '职业',
  `sceneId` int NOT NULL DEFAULT '1' COMMENT '地图ID',
  `userId` int NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `game_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of game_role
-- ----------------------------
INSERT INTO `game_role` VALUES ('1', '霸王', '100', '1', '3', '1', '2020-05-25 10:23:41', '2020-05-25 10:23:45');
INSERT INTO `game_role` VALUES ('2', '刘邦', '100', '2', '3', '2', '2020-05-25 10:23:52', '2020-05-25 10:23:55');

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
