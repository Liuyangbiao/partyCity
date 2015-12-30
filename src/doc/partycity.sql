-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: party
-- ------------------------------------------------------
-- Server version	5.1.63

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) DEFAULT '0' COMMENT '拥有者',
  `who` int(11) DEFAULT '0' COMMENT '被添加人',
  `createTimeLong` bigint(13) DEFAULT '0' COMMENT '创建时间',
  `status` char(1) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '状态',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext3` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext4` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext5` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `extInt1` int(11) DEFAULT '0',
  `extInt2` int(11) DEFAULT '0',
  `extInt3` int(11) DEFAULT '0',
  `extInt4` int(11) DEFAULT '0',
  `extInt5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='通讯录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (2,49,38,0,'','','','','','',0,0,0,0,0),(3,42,49,0,'','','','','','',0,0,0,0,0);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '内容',
  `createTimeLong` bigint(13) DEFAULT '0',
  `partyId` int(11) DEFAULT '0' COMMENT '派对主键',
  `userId` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '推送人ID',
  `status` char(1) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '状态',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext3` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext4` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext5` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `extInt1` int(11) DEFAULT '0',
  `extInt2` int(11) DEFAULT '0',
  `extInt3` int(11) DEFAULT '0',
  `extInt4` int(11) DEFAULT '0',
  `extInt5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'哈哈哈哈哈哈哈哈',0,0,'35','','5','','','','',0,0,0,0,0),(2,'?????????',0,0,'pc_49','','4','','','','',0,0,0,0,0),(3,'?????????',0,0,'pc_49','','4','','','','',0,0,0,0,0);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party`
--

DROP TABLE IF EXISTS `party`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '主题',
  `type` char(3) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '类型',
  `start` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '开始时间',
  `end` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '结束时间',
  `address` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '地址',
  `city` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '城市',
  `count` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '人数',
  `cost` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '费用',
  `inviteObject` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '邀请对象',
  `content` varchar(400) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '描述',
  `createTimeLong` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '创建时间',
  `status` varchar(3) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '状态',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建人',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '是否导入过联系人',
  `ext3` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '派对海报',
  `ext4` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext5` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `extInt1` varchar(200) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '场地名称',
  `extInt2` int(11) DEFAULT '0',
  `extInt3` varchar(200) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '场地名称',
  `extInt4` int(11) DEFAULT '0',
  `extInt5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='派对';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party`
--

LOCK TABLES `party` WRITE;
/*!40000 ALTER TABLE `party` DISABLE KEYS */;
INSERT INTO `party` VALUES (1,'?????????','6','1442566800000','1442226120000','40','010','10','20000','','???????????????????532????537?????????????????????????4???????','1442221254995','0','42','','/Users/Dio/Desktop//files/20150914/2c7535bb-e561-49d7-b57f-d7034276ab0b.jpg','?????','','0',0,'0',0,1),(2,'????','4','1442225100000','1442225400000','30','010','10','2334','','??????  ','1442225147733','0','49','','/Users/Dio/Desktop//files/20150914/97d3291c-16a6-4b58-ad84-b7c9196922aa.jpg','???????','','0',0,'0',0,1),(3,'?????????','8','1442225400000','1442226120000','31','010','22','123456','','??????? ','1442225229937','0','49','','/Users/Dio/Desktop//files/20150914/67c0e76c-7bf3-4a50-99b4-1d72f4bb6363.jpg','????-??????','','0',0,'0',0,1);
/*!40000 ALTER TABLE `party` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partyLog`
--

DROP TABLE IF EXISTS `partyLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partyLog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partyId` int(11) DEFAULT '0' COMMENT '加入派对',
  `userId` int(11) DEFAULT '0' COMMENT '加入人',
  `createTimeLong` bigint(13) DEFAULT '0' COMMENT '加入时间',
  `partyOwner` int(11) DEFAULT '0' COMMENT '派对拥有者',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext3` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext4` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext5` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `extInt1` int(11) DEFAULT '0',
  `extInt2` int(11) DEFAULT '0',
  `extInt3` int(11) DEFAULT '0',
  `extInt4` int(11) DEFAULT '0',
  `extInt5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partyLog`
--

LOCK TABLES `partyLog` WRITE;
/*!40000 ALTER TABLE `partyLog` DISABLE KEYS */;
INSERT INTO `partyLog` VALUES (1,1,49,1442224980385,42,'','','','','',0,0,0,0,0),(2,3,38,1442225264670,49,'','','','','',0,0,0,0,0);
/*!40000 ALTER TABLE `partyLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `place`
--

DROP TABLE IF EXISTS `place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `place` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '场地名称',
  `address` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '地址',
  `count` int(11) DEFAULT '0' COMMENT '最大接纳数',
  `phone` varchar(11) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '手机',
  `froma` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '座机',
  `content` varchar(400) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '描述',
  `avatar` varchar(100) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '图像',
  `createTimeLong` bigint(13) DEFAULT '0' COMMENT '创建时间',
  `status` char(1) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '状态',
  `owner` int(11) DEFAULT '0' COMMENT '拥有者',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '场地类型\n以逗号分割，可以多选',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext3` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext4` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext5` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `extInt1` int(11) DEFAULT '0',
  `extInt2` int(11) DEFAULT '0',
  `extInt3` int(11) DEFAULT '0',
  `extInt4` int(11) DEFAULT '0',
  `extInt5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='场地';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place`
--

LOCK TABLES `place` WRITE;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
INSERT INTO `place` VALUES (30,'???????','???????????8??????????????(???????)',500,'','12345678','    abcdefg','http://mumubar.cn/images/partyplace/1.jpg',0,'1',35,'0','021','','','',0,0,0,0,0),(31,'????-??????','???????????1200?(????)',200,'','4001816688','','http://mumubar.cn/images/partyplace/2.jpg',0,'1',2,'2','021','','','',0,0,0,0,0),(32,'夜明珠国际娱乐会所','上海市浦东新区崂山路588号宝安大厦4楼c座(近潍坊路)',200,'','51156600','','http://mumubar.cn/images/partyplace/4.jpg',0,'1',2,'3','021','','','',0,0,0,0,0),(33,'东土温泉会所','上海市浦东新区东建路528号-A(近锦绣路)',150,'','50595773','','http://mumubar.cn/images/partyplace/5.jpg',0,'1',2,'3','021','','','',0,0,0,0,0),(34,'极乐汤金沙江温泉馆','上海市祁连山南路398号(近同普路)',150,'','62310660','','http://mumubar.cn/images/partyplace/8.jpg',0,'1',2,'2','021','','','',0,0,0,0,0),(35,'现代军事体育俱乐部','上海市卢湾区淮海中路701号7楼(近思南路)',50,'','53829955',NULL,'http://mumubar.cn/images/partyplace/9.jpg',0,'1',2,'1','021','','','',0,0,0,0,0),(36,'凯撒国际商务会所','上海市静安区石门二路301号(近新闸路)',150,'15221920392',NULL,'','http://mumubar.cn/images/partyplace/10.jpg',0,'1',2,'2','021','','','',0,0,0,0,0),(37,'兰亭叙','上海市松江区涞亭南路288号(九亭大街)',60,'','33731517','','http://mumubar.cn/images/partyplace/11.jpg',0,'1',2,'0','021','','','',0,0,0,0,0),(38,'九五皇宫娱乐会所','上海市闸北区三泉路1128号(近共康路)',200,'13732919191','','','http://mumubar.cn/images/partyplace/14.jpg',0,'1',2,'3','021','','','',0,0,0,0,0),(39,'GreenValley金庭马术中心','上海市长宁区协和路1号(近新潮路)',100,'','62095877','','http://mumubar.cn/images/partyplace/16.jpg',0,'1',2,'1','021','','','',0,0,0,0,0),(40,'上海游艇会','上海市普陀区大渡河路160号(近光复西路) ',100,'','4001817788','','http://mumubar.cn/images/partyplace/19.jpg',0,'1',2,'0','021','','','',0,0,0,0,0),(41,'虹口高尔夫练习场','上海市虹口区东江湾路444号(地铁三号线)',100,'','56500321','','http://mumubar.cn/images/partyplace/20.jpg',0,'1',2,'1','021','','','',0,0,0,0,0);
/*!40000 ALTER TABLE `place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) DEFAULT '0' COMMENT '创建人',
  `title` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '内容',
  `start` bigint(13) DEFAULT '0' COMMENT '开始时间',
  `end` bigint(13) DEFAULT '0' COMMENT '结束时间',
  `push` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '推送设置',
  `createTimeLong` bigint(13) DEFAULT '0' COMMENT '创建时间',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '类型，标志是否派对以及事项',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext3` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext4` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext5` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `extInt1` int(11) DEFAULT '0',
  `extInt2` int(11) DEFAULT '0',
  `extInt3` int(11) DEFAULT '0',
  `extInt4` int(11) DEFAULT '0',
  `extInt5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='日程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,42,'?????????',1442566800000,1443170400000,'',1442221258556,'2','1','0','','',1,0,0,0,0),(2,49,'?????????',1442566800000,1443170400000,'',1442224980398,'1','1','','','',1,0,0,0,0),(3,49,'????',1442225100000,1442225400000,'',1442225147749,'2','2','0','','',0,0,0,0,0),(4,49,'?????????',1442225400000,1442225700000,'',1442225229956,'2','3','0','','',1,0,0,0,0),(5,38,'?????????',1442225400000,1442225700000,'',1442225264680,'1','3','','','',1,0,0,0,0);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT '' COMMENT '姓名',
  `pwd` varchar(45) NOT NULL DEFAULT '' COMMENT '密码',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机',
  `birthday` varchar(45) DEFAULT '' COMMENT '生日',
  `age` varchar(3) DEFAULT '' COMMENT '年龄',
  `avatar` varchar(200) DEFAULT '' COMMENT '头像',
  `sex` char(1) DEFAULT '1' COMMENT '性别',
  `city` varchar(20) DEFAULT '' COMMENT '地区',
  `industry` varchar(45) DEFAULT '' COMMENT '行业',
  `company` varchar(45) DEFAULT '' COMMENT '公司',
  `position` varchar(45) DEFAULT '' COMMENT '职位',
  `code` varchar(45) DEFAULT '' COMMENT '邀请码',
  `parent` varchar(45) DEFAULT '' COMMENT '邀请人',
  `parentId` int(11) DEFAULT NULL COMMENT '邀请人主键',
  `status` char(1) DEFAULT '0' COMMENT '状态\n0：待审核\n1：可使用\n2：冻结中',
  `createTimeLong` bigint(13) DEFAULT '0' COMMENT '创建时间',
  `ext1` varchar(45) DEFAULT '',
  `ext2` varchar(45) DEFAULT '',
  `ext3` varchar(45) DEFAULT '',
  `ext4` varchar(45) DEFAULT '',
  `ext5` varchar(45) DEFAULT '',
  `extInt1` int(11) DEFAULT '0',
  `extInt2` int(11) DEFAULT '0',
  `extInt3` int(11) DEFAULT '0',
  `extInt4` int(11) DEFAULT '0',
  `extInt5` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (35,'陈春梅','96e79218965eb72c92a549dd5a330112','18600000002','182707200','35','/Users/Dio/Desktop//files/20150922/871b009b-4c34-46f9-943d-0e3c741e1d82.jpg','1','上海市','0','中晋股权投资基金管理（上海）有限公司','财务总监','','李家骥',2,'1',1442888519000,'1','1','','','',0,0,0,0,0),(36,'戴正啸','96e79218965eb72c92a549dd5a330112','18600000003','265651200','42','http://mumubar.cn/images/partyphoto/3.jpg','1','上海市','0','上海金蕴投资管理有限公司','客户总监','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(37,'邓胜鑫','96e79218965eb72c92a549dd5a330112','18600000004','-303811200','55','http://mumubar.cn/images/partyphoto/4.jpeg','1','上海市','0','银利宝（上海）投资股份有限公司','副总裁','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(38,'董慧文','96e79218965eb72c92a549dd5a330112','18600000005','-221385600','53','http://mumubar.cn/images/partyphoto/5.jpg','1','上海市','0','宜信汇才商务顾问有限公司','副总裁','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(39,'杜恒','96e79218965eb72c92a549dd5a330112','18600000006','-60508800','47','http://mumubar.cn/images/partyphoto/6.jpg','1','上海市','1','上海千慕大信息科技有限公司','COO','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(40,'冯国平','96e79218965eb72c92a549dd5a330112','18600000007','292176000','41','http://mumubar.cn/images/partyphoto/7.jpg','1','上海市','1','上海也码信息技术有限公司','CEO','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(41,'付贤兵','96e79218965eb72c92a549dd5a330112','18600000008','-107510400','49','http://mumubar.cn/images/partyphoto/8.jpg','1','上海市','1','上海火盛电子贸易有限公司','CEO','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(42,'高占昆','96e79218965eb72c92a549dd5a330112','18600000009','194544000','39','http://mumubar.cn/images/partyphoto/9.jpg','1','上海市','1','琛胜（上海）科技发展有限公司','CTO','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(43,'郭瑞','96e79218965eb72c92a549dd5a330112','18600000010','-115718400','49','http://mumubar.cn/images/partyphoto/10.jpg','1','上海市','1','上海时域电子商务有限公司','CEO','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(44,'韩军','96e79218965eb72c92a549dd5a330112','18600000011','-157622400','50','http://mumubar.cn/images/partyphoto/11.jpg','1','上海市','2','上海索纳特酒店管理有限公司','总经理','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(45,'胡文彬','96e79218965eb72c92a549dd5a330112','18600000012','39456000','44','http://mumubar.cn/images/partyphoto/12.jpg','1','上海市','2','上海汇居房地产经纪有限公司','销售总监','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(46,'黄若宇','96e79218965eb72c92a549dd5a330112','18600000013','107884800','42','http://mumubar.cn/images/partyphoto/13.jpg','1','上海市','2','上海众原房地产投资顾问有限公司','投资总监','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(47,'姜兴宇','96e79218965eb72c92a549dd5a330112','18600000014','-242035200','53','http://mumubar.cn/images/partyphoto/14.jpg','1','上海市','2','上海申韵实业有限公司','副总裁','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(48,'康跃','96e79218965eb72c92a549dd5a330112','18600000015','178992000','40','http://mumubar.cn/images/partyphoto/15.jpg','1','上海市','2','上海华派地产经纪不动产公司','销售总监','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(49,'李昌波','96e79218965eb72c92a549dd5a330112','18600000016','-370857600','57','http://mumubar.cn/images/partyphoto/16.jpg','1','上海市','3','中国平安上海分公司','副行长','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(50,'李道昕','96e79218965eb72c92a549dd5a330112','18600000017','240336000','48','http://mumubar.cn/images/partyphoto/17.jpg','1','上海市','3','中信小额投资贷款公司','总经理','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(51,'刘佳龙','96e79218965eb72c92a549dd5a330112','18600000018','168364800','40','http://mumubar.cn/images/partyphoto/18.jpg','1','上海市','3','新华人寿上海分公司','客户总监','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(52,'刘涛','96e79218965eb72c92a549dd5a330112','18600000019','226080000','42','http://mumubar.cn/images/partyphoto/19.jpg','1','上海市','3','中信建投期货公司','总经理','','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(53,'刘永翔','96e79218965eb72c92a549dd5a330112','18600000020','-151142400','50','http://mumubar.cn/images/partyphoto/20.jpg','1','上海市','3','中国太平洋人寿保险股份有限公司','总经理','0a74465d0b','李家骥',2,'1',1439369803399,'','','','','',0,0,0,0,0),(54,'','1111111','13811965877','','','','1','','','','','19b1bd2481','李家骥',2,'1',1442558591373,'','','','','',0,0,0,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vipCode`
--

DROP TABLE IF EXISTS `vipCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vipCode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vipCode` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '邀请码',
  `owner` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '分发对象',
  `createTimeLong` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '创建时间',
  `status` varchar(45) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '状态',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext3` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext4` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `ext5` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=247 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vipCode`
--

LOCK TABLES `vipCode` WRITE;
/*!40000 ALTER TABLE `vipCode` DISABLE KEYS */;
INSERT INTO `vipCode` VALUES (47,'QCPD001','83','1441952444609','0','','','','',''),(48,'QCPD002','83','1441952444609','0','','','','',''),(49,'QCPD003','83','1441952444609','0','','','','',''),(50,'QCPD004','83','1441952444609','0','','','','',''),(51,'QCPD005','83','1441952444609','0','','','','',''),(52,'QCPD006','83','1441952444609','0','','','','',''),(53,'QCPD007','83','1441952444609','0','','','','',''),(54,'QCPD008','83','1441952444609','0','','','','',''),(55,'QCPD009','83','1441952444609','0','','','','',''),(56,'QCPD010','83','1441952444609','0','','','','',''),(57,'QCPD011','83','1441952444609','0','','','','',''),(58,'QCPD012','83','1441952444609','0','','','','',''),(59,'QCPD013','83','1441952444609','0','','','','',''),(60,'QCPD014','83','1441952444609','0','','','','',''),(61,'QCPD015','83','1441952444609','0','','','','',''),(62,'QCPD016','83','1441952444609','0','','','','',''),(63,'QCPD017','83','1441952444609','0','','','','',''),(64,'QCPD018','83','1441952444609','0','','','','',''),(65,'QCPD019','83','1441952444609','0','','','','',''),(66,'QCPD020','83','1441952444609','0','','','','',''),(67,'QCPD021','83','1441952444609','0','','','','',''),(68,'QCPD022','83','1441952444609','0','','','','',''),(69,'QCPD023','83','1441952444609','0','','','','',''),(70,'QCPD024','83','1441952444609','0','','','','',''),(71,'QCPD025','83','1441952444609','0','','','','',''),(72,'QCPD026','83','1441952444609','0','','','','',''),(73,'QCPD027','83','1441952444609','0','','','','',''),(74,'QCPD028','83','1441952444609','0','','','','',''),(75,'QCPD029','83','1441952444609','0','','','','',''),(76,'QCPD030','83','1441952444609','0','','','','',''),(77,'QCPD031','83','1441952444609','0','','','','',''),(78,'QCPD032','83','1441952444609','0','','','','',''),(79,'QCPD033','83','1441952444609','0','','','','',''),(80,'QCPD034','83','1441952444609','0','','','','',''),(81,'QCPD035','83','1441952444609','0','','','','',''),(82,'QCPD036','83','1441952444609','0','','','','',''),(83,'QCPD037','83','1441952444609','0','','','','',''),(84,'QCPD038','83','1441952444609','0','','','','',''),(85,'QCPD039','83','1441952444609','0','','','','',''),(86,'QCPD040','83','1441952444609','0','','','','',''),(87,'QCPD041','83','1441952444609','0','','','','',''),(88,'QCPD042','83','1441952444609','0','','','','',''),(89,'QCPD043','83','1441952444609','0','','','','',''),(90,'QCPD044','83','1441952444609','0','','','','',''),(91,'QCPD045','83','1441952444609','0','','','','',''),(92,'QCPD046','83','1441952444609','0','','','','',''),(93,'QCPD047','83','1441952444609','0','','','','',''),(94,'QCPD048','83','1441952444609','0','','','','',''),(95,'QCPD049','83','1441952444609','0','','','','',''),(96,'QCPD050','83','1441952444609','0','','','','',''),(97,'QCPD051','83','1441952444609','0','','','','',''),(98,'QCPD052','83','1441952444609','0','','','','',''),(99,'QCPD053','83','1441952444609','0','','','','',''),(100,'QCPD054','83','1441952444609','0','','','','',''),(101,'QCPD055','83','1441952444609','0','','','','',''),(102,'QCPD056','83','1441952444609','0','','','','',''),(103,'QCPD057','83','1441952444609','0','','','','',''),(104,'QCPD058','83','1441952444609','0','','','','',''),(105,'QCPD059','83','1441952444609','0','','','','',''),(106,'QCPD060','83','1441952444609','0','','','','',''),(107,'QCPD061','83','1441952444609','0','','','','',''),(108,'QCPD062','83','1441952444609','0','','','','',''),(109,'QCPD063','83','1441952444609','0','','','','',''),(110,'QCPD064','83','1441952444609','0','','','','',''),(111,'QCPD065','83','1441952444609','0','','','','',''),(112,'QCPD066','83','1441952444609','0','','','','',''),(113,'QCPD067','83','1441952444609','0','','','','',''),(114,'QCPD068','83','1441952444609','0','','','','',''),(115,'QCPD069','83','1441952444609','0','','','','',''),(116,'QCPD070','83','1441952444609','0','','','','',''),(117,'QCPD071','83','1441952444609','0','','','','',''),(118,'QCPD072','83','1441952444609','0','','','','',''),(119,'QCPD073','83','1441952444609','0','','','','',''),(120,'QCPD074','83','1441952444609','0','','','','',''),(121,'QCPD075','83','1441952444609','0','','','','',''),(122,'QCPD076','83','1441952444609','0','','','','',''),(123,'QCPD077','83','1441952444609','0','','','','',''),(124,'QCPD078','83','1441952444609','0','','','','',''),(125,'QCPD079','83','1441952444609','0','','','','',''),(126,'QCPD080','83','1441952444609','0','','','','',''),(127,'QCPD081','83','1441952444609','0','','','','',''),(128,'QCPD082','83','1441952444609','0','','','','',''),(129,'QCPD083','83','1441952444609','0','','','','',''),(130,'QCPD084','83','1441952444609','0','','','','',''),(131,'QCPD085','83','1441952444609','0','','','','',''),(132,'QCPD086','83','1441952444609','0','','','','',''),(133,'QCPD087','83','1441952444609','0','','','','',''),(134,'QCPD088','83','1441952444609','0','','','','',''),(135,'QCPD089','83','1441952444609','0','','','','',''),(136,'QCPD090','83','1441952444609','0','','','','',''),(137,'QCPD091','83','1441952444609','0','','','','',''),(138,'QCPD092','83','1441952444609','0','','','','',''),(139,'QCPD093','83','1441952444609','0','','','','',''),(140,'QCPD094','83','1441952444609','0','','','','',''),(141,'QCPD095','83','1441952444609','0','','','','',''),(142,'QCPD096','83','1441952444609','0','','','','',''),(143,'QCPD097','83','1441952444609','0','','','','',''),(144,'QCPD098','83','1441952444609','0','','','','',''),(145,'QCPD099','83','1441952444609','0','','','','',''),(146,'QCPD100','83','1441952444609','0','','','','',''),(147,'QCPD101','84','1441952444609','0','','','','',''),(148,'QCPD102','84','1441952444609','0','','','','',''),(149,'QCPD103','84','1441952444609','0','','','','',''),(150,'QCPD104','84','1441952444609','0','','','','',''),(151,'QCPD105','84','1441952444609','0','','','','',''),(152,'QCPD106','84','1441952444609','0','','','','',''),(153,'QCPD107','84','1441952444609','0','','','','',''),(154,'QCPD108','84','1441952444609','0','','','','',''),(155,'QCPD109','84','1441952444609','0','','','','',''),(156,'QCPD110','84','1441952444609','0','','','','',''),(157,'QCPD111','84','1441952444609','0','','','','',''),(158,'QCPD112','84','1441952444609','0','','','','',''),(159,'QCPD113','84','1441952444609','0','','','','',''),(160,'QCPD114','84','1441952444609','0','','','','',''),(161,'QCPD115','84','1441952444609','0','','','','',''),(162,'QCPD116','84','1441952444609','0','','','','',''),(163,'QCPD117','84','1441952444609','0','','','','',''),(164,'QCPD118','84','1441952444609','0','','','','',''),(165,'QCPD119','84','1441952444609','0','','','','',''),(166,'QCPD120','84','1441952444609','0','','','','',''),(167,'QCPD121','84','1441952444609','0','','','','',''),(168,'QCPD122','84','1441952444609','0','','','','',''),(169,'QCPD123','84','1441952444609','0','','','','',''),(170,'QCPD124','84','1441952444609','0','','','','',''),(171,'QCPD125','84','1441952444609','0','','','','',''),(172,'QCPD126','84','1441952444609','0','','','','',''),(173,'QCPD127','84','1441952444609','0','','','','',''),(174,'QCPD128','84','1441952444609','0','','','','',''),(175,'QCPD129','84','1441952444609','0','','','','',''),(176,'QCPD130','84','1441952444609','0','','','','',''),(177,'QCPD131','84','1441952444609','0','','','','',''),(178,'QCPD132','84','1441952444609','0','','','','',''),(179,'QCPD133','84','1441952444609','0','','','','',''),(180,'QCPD134','84','1441952444609','0','','','','',''),(181,'QCPD135','84','1441952444609','0','','','','',''),(182,'QCPD136','84','1441952444609','0','','','','',''),(183,'QCPD137','84','1441952444609','0','','','','',''),(184,'QCPD138','84','1441952444609','0','','','','',''),(185,'QCPD139','84','1441952444609','0','','','','',''),(186,'QCPD140','84','1441952444609','0','','','','',''),(187,'QCPD141','84','1441952444609','0','','','','',''),(188,'QCPD142','84','1441952444609','0','','','','',''),(189,'QCPD143','84','1441952444609','0','','','','',''),(190,'QCPD144','84','1441952444609','0','','','','',''),(191,'QCPD145','84','1441952444609','0','','','','',''),(192,'QCPD146','84','1441952444609','0','','','','',''),(193,'QCPD147','84','1441952444609','0','','','','',''),(194,'QCPD148','84','1441952444609','0','','','','',''),(195,'QCPD149','84','1441952444609','0','','','','',''),(196,'QCPD150','84','1441952444609','0','','','','',''),(197,'QCPD151','84','1441952444609','0','','','','',''),(198,'QCPD152','84','1441952444609','0','','','','',''),(199,'QCPD153','84','1441952444609','0','','','','',''),(200,'QCPD154','84','1441952444609','0','','','','',''),(201,'QCPD155','84','1441952444609','0','','','','',''),(202,'QCPD156','84','1441952444609','0','','','','',''),(203,'QCPD157','84','1441952444609','0','','','','',''),(204,'QCPD158','84','1441952444609','0','','','','',''),(205,'QCPD159','84','1441952444609','0','','','','',''),(206,'QCPD160','84','1441952444609','0','','','','',''),(207,'QCPD161','84','1441952444609','0','','','','',''),(208,'QCPD162','84','1441952444609','0','','','','',''),(209,'QCPD163','84','1441952444609','0','','','','',''),(210,'QCPD164','84','1441952444609','0','','','','',''),(211,'QCPD165','84','1441952444609','0','','','','',''),(212,'QCPD166','84','1441952444609','0','','','','',''),(213,'QCPD167','84','1441952444609','0','','','','',''),(214,'QCPD168','84','1441952444609','0','','','','',''),(215,'QCPD169','84','1441952444609','0','','','','',''),(216,'QCPD170','84','1441952444609','0','','','','',''),(217,'QCPD171','84','1441952444609','0','','','','',''),(218,'QCPD172','84','1441952444609','0','','','','',''),(219,'QCPD173','84','1441952444609','0','','','','',''),(220,'QCPD174','84','1441952444609','0','','','','',''),(221,'QCPD175','84','1441952444609','0','','','','',''),(222,'QCPD176','84','1441952444609','0','','','','',''),(223,'QCPD177','84','1441952444609','0','','','','',''),(224,'QCPD178','84','1441952444609','0','','','','',''),(225,'QCPD179','84','1441952444609','0','','','','',''),(226,'QCPD180','84','1441952444609','0','','','','',''),(227,'QCPD181','84','1441952444609','0','','','','',''),(228,'QCPD182','84','1441952444609','0','','','','',''),(229,'QCPD183','84','1441952444609','0','','','','',''),(230,'QCPD184','84','1441952444609','0','','','','',''),(231,'QCPD185','84','1441952444609','0','','','','',''),(232,'QCPD186','84','1441952444609','0','','','','',''),(233,'QCPD187','84','1441952444609','0','','','','',''),(234,'QCPD188','84','1441952444609','0','','','','',''),(235,'QCPD189','84','1441952444609','0','','','','',''),(236,'QCPD190','84','1441952444609','0','','','','',''),(237,'QCPD191','84','1441952444609','0','','','','',''),(238,'QCPD192','84','1441952444609','0','','','','',''),(239,'QCPD193','84','1441952444609','0','','','','',''),(240,'QCPD194','84','1441952444609','0','','','','',''),(241,'QCPD195','84','1441952444609','0','','','','',''),(242,'QCPD196','84','1441952444609','0','','','','',''),(243,'QCPD197','84','1441952444609','0','','','','',''),(244,'QCPD198','84','1441952444609','0','','','','',''),(245,'QCPD199','84','1441952444609','0','','','','',''),(246,'QCPD200','84','1441952444609','0','','','','','');
/*!40000 ALTER TABLE `vipCode` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-30 13:15:36
