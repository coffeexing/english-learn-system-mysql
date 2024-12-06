/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.7.24-log : Database - english_learn_sys
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`english_learn_sys` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `english_learn_sys`;

/*Table structure for table `english_book` */

DROP TABLE IF EXISTS `english_book`;

CREATE TABLE `english_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_code` varchar(100) NOT NULL,
  `book_name` varchar(200) NOT NULL,
  `file_path` varchar(500) NOT NULL,
  `description` text,
  `status` smallint(6) NOT NULL,
  `base_practice_num` int(11) DEFAULT NULL,
  `translate_practice_num` int(11) DEFAULT NULL,
  `exam_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l6rrt0kvgi4jer84tox9yp17n` (`book_code`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `english_book` */

insert  into `english_book`(`id`,`book_code`,`book_name`,`file_path`,`description`,`status`,`base_practice_num`,`translate_practice_num`,`exam_num`) values (24,'senior_vocabulary_1','高中词汇书','data/book/高中词汇书#senior_vocabulary_1.xlsx','测试版；词汇量：10',1,2,20,5),(25,'junior_vocabulary_01','初中英语词汇书1','data/book/初中英语词汇书1#junior_vocabulary_01.xlsx','测试专用',1,4,4,4),(27,'6109f95e6b984b9e9fa9c4ffc7fbc951','英语词汇书-高阶01','data/book/英语词汇书-高阶01#6109f95e6b984b9e9fa9c4ffc7fbc951.xlsx','测试用',1,5,5,5),(29,'c75e1144bc2646e0ad503f842f410df3','小学单词书（一阶段）','data/book/小学单词书（一阶段）#c75e1144bc2646e0ad503f842f410df3.xlsx','导入词汇兼容测试：单词自动识别',1,NULL,NULL,NULL);

/*Table structure for table `english_word` */

DROP TABLE IF EXISTS `english_word`;

CREATE TABLE `english_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_code` varchar(100) NOT NULL,
  `word_type` smallint(6) DEFAULT NULL,
  `word` varchar(100) NOT NULL,
  `chinese` varchar(200) NOT NULL,
  `pronounce_file` varchar(500) DEFAULT NULL,
  `description` text,
  `status` smallint(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

/*Data for the table `english_word` */

insert  into `english_word`(`id`,`book_code`,`word_type`,`word`,`chinese`,`pronounce_file`,`description`,`status`) values (44,'senior_vocabulary_1',1,'abandon','1.vt放弃, 遗弃；2.vt放任, 狂热','data/pronounce/senior_vocabulary_1/abandon--_gb_1.mp3','a+band+on一个乐队在演出，乐队演出很放纵',1),(45,'senior_vocabulary_1',1,'abnormal','1.adj反常的, 变态的','data/pronounce/senior_vocabulary_1/abnormal--_gb_1.mp3','ab=相反，变坏 normal正常',1),(46,'senior_vocabulary_1',1,'abrupt','1.adj.突然的, 意想不到的;2.陡的, 险峻的','data/pronounce/senior_vocabulary_1/abrupt--_gb_1.mp3','rupt=break',1),(47,'senior_vocabulary_1',1,'absence','1.n.缺席；2.n.不注意; 心不在焉','data/pronounce/senior_vocabulary_1/absence--_gb_1.mp3','sence 存在，相关，ab相反 缺席的',1),(48,'senior_vocabulary_1',1,'absolute','1.adj.绝对的；完全的；2.adj 确定的','data/pronounce/senior_vocabulary_1/absolute--_gb_1.mp3','solut 松，解开 ',1),(49,'senior_vocabulary_1',1,'absorb','1. vt. 吸收（液体）；2. vt. 支付；负担','data/pronounce/senior_vocabulary_1/absorb--_gb_1.mp3','sorb吸收， absorb吸收掉',1),(50,'senior_vocabulary_1',1,'abstract','1.n. 摘要, 概要, 抽象','data/pronounce/senior_vocabulary_1/abstract--_gb_1.mp3','abs(离去)+tract(拉)→把大意从文中拉出来→摘要 ',1),(51,'senior_vocabulary_1',1,'abundance','1. n. 丰富, 充裕, 丰富充裕','data/pronounce/senior_vocabulary_1/abundance--_gb_1.mp3','abundance=a + bun(小面包)+ dance,形象化记忆,很多的蚂蚁围着一块小面包在跳舞,因为丰收了',1),(52,'senior_vocabulary_1',1,'yield','1.v.出产，生长，生产；2.vi.（~to）屈服，屈从','data/pronounce/senior_vocabulary_1/yield--_gb_1.mp3','yi(已)+eld(老)----已经老了, 对有些事情就得屈服了',1),(53,'junior_vocabulary_01',1,'hello','你好；喂','data/pronounce/junior_vocabulary_01/hello--_gb_1.mp3','1.（用以打招呼或唤起注意）喂，你好。如：Hello, Jim! How are you?嗨！吉姆!你好吗?\r\n2.（用作打电话时的招呼语）喂\r\n3.（表示惊讶等）嘿；啊\r\n4.在跟打招呼用hello是不礼貌的，应该用Hi才是有礼貌的。',1),(54,'junior_vocabulary_01',1,'legendary','传奇','data/pronounce/junior_vocabulary_01/legendary--_gb_1.mp3','xxx',1),(55,'junior_vocabulary_01',2,'pay attention to','集中注意力于。。。',NULL,'xxx',1),(56,'junior_vocabulary_01',1,'unbelievable','难以置信','data/pronounce/junior_vocabulary_01/unbelievable--_gb_1.mp3','形容对某些事情感到非常惊讶',1),(57,'6109f95e6b984b9e9fa9c4ffc7fbc951',1,'engineer','工程师;设计师;机修工;技师;','data/pronounce/6109f95e6b984b9e9fa9c4ffc7fbc951/engineer--_gb_1.mp3','n.工程师;设计师;机修工;技师;技工;(船上的)轮机手;(飞机上的)机械师;工兵\nvt.密谋策划;设计制造;改变…的基因(或遗传)结构',1),(58,'6109f95e6b984b9e9fa9c4ffc7fbc951',1,'material','布料;原料;物质的;客观存在的;','data/pronounce/6109f95e6b984b9e9fa9c4ffc7fbc951/material--_gb_1.mp3','n.布料;原料;(某一活动所需的)材料;素材;节目\nadj.实际的(非精神需求的);物质的;客观存在的;重要的;必要的',1),(60,'6109f95e6b984b9e9fa9c4ffc7fbc951',1,'comprehensive','综合的; 全部的; 所有的; ','data/pronounce/6109f95e6b984b9e9fa9c4ffc7fbc951/comprehensive--_gb_1.mp3','\nadj. 综合的; 全部的; 所有的; (几乎)无所不包的; 详尽的; 综合性的(接收各种资质的学生);\nn. (英国为各种资质的学生设立的)综合中学;\n',1),(62,'6109f95e6b984b9e9fa9c4ffc7fbc951',1,'foundation','地基;房基;基础;基本原理；','data/pronounce/6109f95e6b984b9e9fa9c4ffc7fbc951/foundation--_gb_1.mp3','地基;房基;基础;基本原理;根据;基金会;（机构或组织的）创建，创办;（化妆打底用的）粉底霜',1),(63,'6109f95e6b984b9e9fa9c4ffc7fbc951',1,'tortoise','乌龟; 龟; 陆龟;','data/pronounce/6109f95e6b984b9e9fa9c4ffc7fbc951/tortoise--_gb_1.mp3','乌龟; 龟; 陆龟;',1),(67,'c75e1144bc2646e0ad503f842f410df3',1,'although [ɔːlˈðəʊ]','虽然；尽管','data/pronounce/c75e1144bc2646e0ad503f842f410df3/although--_gb_1.mp3','conj.',1),(68,'c75e1144bc2646e0ad503f842f410df3',1,'always [ˈɔːlweɪz]','总是；一直；永远','data/pronounce/c75e1144bc2646e0ad503f842f410df3/always--_gb_1.mp3','ad.',1),(69,'c75e1144bc2646e0ad503f842f410df3',1,'American [əˈmerɪkə]','美国；美洲','data/pronounce/c75e1144bc2646e0ad503f842f410df3/american--_gb_1.mp3','n.',1),(70,'c75e1144bc2646e0ad503f842f410df3',1,'answer  [ˈɑːnsə(r); (US) ˈænsər]','回答，答复；答案 回答，答复；回信（作出）答案','data/pronounce/c75e1144bc2646e0ad503f842f410df3/answer--_gb_1.mp3','I couldn’t answer all the exam questions.\n我没能作答全部的试卷问题。',1);

/*Table structure for table `exam_score` */

DROP TABLE IF EXISTS `exam_score`;

CREATE TABLE `exam_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_code` varchar(100) NOT NULL,
  `score` int(11) NOT NULL,
  `point` int(11) NOT NULL,
  `exam_time` datetime(6) NOT NULL,
  `total_questions` int(11) DEFAULT NULL,
  `answer_true` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `exam_score` */

insert  into `exam_score`(`id`,`user_id`,`book_code`,`score`,`point`,`exam_time`,`total_questions`,`answer_true`) values (2,8,'senior_vocabulary_1',80,0,'2021-06-01 22:33:32.469000',10,8),(3,8,'senior_vocabulary_1',100,10,'2021-06-01 22:39:05.041000',10,10),(6,1,'senior_vocabulary_1',78,0,'2021-06-06 15:12:09.303000',9,7),(7,1,'senior_vocabulary_1',89,0,'2021-06-06 15:37:52.799000',9,8),(8,1,'senior_vocabulary_1',100,10,'2021-06-06 15:42:34.753000',9,9),(9,1,'senior_vocabulary_1',89,0,'2021-06-06 16:04:04.247000',9,8),(10,1,'senior_vocabulary_1',100,10,'2021-06-06 16:11:22.697000',9,9),(11,1,'senior_vocabulary_1',11,0,'2021-06-06 16:14:44.424000',9,1),(12,1,'senior_vocabulary_1',100,10,'2021-06-06 17:23:11.299000',5,5),(13,1,'senior_vocabulary_1',60,0,'2023-01-20 00:47:10.619000',5,3);

/*Table structure for table `sys_department` */

DROP TABLE IF EXISTS `sys_department`;

CREATE TABLE `sys_department` (
  `node` int(11) NOT NULL AUTO_INCREMENT,
  `parent_node` int(11) NOT NULL,
  `is_node` tinyint(1) NOT NULL,
  `is_directory` tinyint(1) NOT NULL,
  `dept_name` varchar(50) NOT NULL,
  `rank` int(11) NOT NULL,
  PRIMARY KEY (`node`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sys_department` */

insert  into `sys_department`(`node`,`parent_node`,`is_node`,`is_directory`,`dept_name`,`rank`) values (1,0,1,1,'系统级',0);

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `node` int(11) NOT NULL AUTO_INCREMENT,
  `parent_node` int(11) NOT NULL,
  `is_node` tinyint(1) NOT NULL,
  `is_directory` tinyint(1) NOT NULL,
  `title` varchar(50) NOT NULL,
  `type` smallint(6) NOT NULL,
  `open_type` varchar(50) DEFAULT NULL,
  `icon` varchar(300) DEFAULT NULL,
  `href` varchar(300) DEFAULT NULL,
  `rank` int(11) NOT NULL,
  PRIMARY KEY (`node`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`node`,`parent_node`,`is_node`,`is_directory`,`title`,`type`,`open_type`,`icon`,`href`,`rank`) values (2,0,1,0,'系统管理',0,'_iframe','layui-icon layui-icon-set-fill','sys/manage/user',50),(3,2,1,0,'菜单管理',1,'_iframe','layui-icon layui-icon layui-icon-diamond','system/menu/main',5),(8,0,1,0,'英语词汇管理',1,'_iframe','layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon-fire','english/word/main',18),(10,0,1,0,'英语书籍管理',1,'_iframe','layui-icon layui-icon layui-icon layui-icon layui-icon-component','english/book/main',17),(11,0,1,0,'成绩单管理',1,'_iframe','layui-icon layui-icon layui-icon layui-icon-upload','english/score/main',39),(12,2,1,0,'用户管理',1,'_iframe','layui-icon layui-icon layui-icon layui-icon layui-icon layui-icon-user','system/user/main',0),(13,2,1,0,'角色管理',1,'_iframe','layui-icon layui-icon-dialogue','system/role/main',2),(14,2,1,0,'部门管理',1,'_iframe','layui-icon layui-icon-group','system/dept/main',3),(15,0,1,0,'个人中心',0,NULL,'layui-icon layui-icon layui-icon layui-icon layui-icon-username',NULL,10),(17,15,1,0,'基本资料',1,'_iframe','layui-icon layui-icon-rate-solid','/system/user/own_center',1),(18,0,1,0,'首页',1,'_iframe','layui-icon layui-icon layui-icon layui-icon layui-icon-theme','/english/dashboard',1),(19,0,1,0,'英语学习',1,'_iframe','layui-icon layui-icon layui-icon layui-icon-read','/english/study/main',12);

/*Table structure for table `sys_resource` */

DROP TABLE IF EXISTS `sys_resource`;

CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL,
  `resource_name` varchar(100) NOT NULL,
  `uri` varchar(300) NOT NULL,
  `type` varchar(20) NOT NULL,
  `operation` varchar(20) NOT NULL,
  `description` text,
  `button` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_resource` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(30) NOT NULL,
  `description` text,
  `status` smallint(6) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_mwbqlu5c82jfd2w9oa9d6e87d` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role`,`description`,`status`) values (1,'超级管理员','拥有系统内所有权限！',1),(2,'学生','可查看数据面板、编辑个人信息、英语练习、英语综合测试。',1);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` varchar(100) NOT NULL,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`) values ('3172dd0073b54e55911107642ed17132',2,18),('488b976768ca44379e278fe2e8d9368f',2,15),('8c645a7489524ad9ae7a555261e78a6f',2,17),('a1b6ec1b136442d59ca2404a8f88e20d',2,19),('dab2fa2cc4b24e139545270708acde94',2,11);

/*Table structure for table `sys_role_resource` */

DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `id` varchar(100) NOT NULL,
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_resource` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(200) NOT NULL,
  `real_name` varchar(30) DEFAULT NULL,
  `avatar` varchar(300) NOT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `dept` varchar(30) DEFAULT NULL,
  `duty` text,
  `phone` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `id_number` varchar(30) DEFAULT NULL,
  `create_time` datetime(6) NOT NULL,
  `last_modify_time` datetime(6) NOT NULL,
  `last_login_time` datetime(6) DEFAULT NULL,
  `status` smallint(6) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`),
  UNIQUE KEY `UK_emllwygadip430te0o46qf194` (`id_number`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`username`,`password`,`real_name`,`avatar`,`sex`,`dept`,`duty`,`phone`,`email`,`id_number`,`create_time`,`last_modify_time`,`last_login_time`,`status`) values (1,'SuperAdmin','$2a$10$xTFDod042ymCBOiTfTHPs.8Xs8qaox1Le2su4ihJ3bIFH5FHZBbMK','超级管理员','/admin/images/act.jpg','1','系统级','超级管理员；系统根用户','13871231212','11912321@qq.com',NULL,'2021-04-26 03:49:45.319000','2021-04-29 23:31:25.182000',NULL,1),(8,'stu123','$2a$10$mFbbLUvibyFU2QCUQE25S.UARZkz3N3plqqiLhUCycqLQPh.VI3du','学生测试账号1','/admin/images/account/portrait/default/avatar.jpg','0','系统级',NULL,'1381231231','1982139123@qq.com',NULL,'2021-05-30 10:27:36.803000','2023-01-20 00:56:10.636000',NULL,1),(9,'yingying','$2a$10$y8AFv1KJmMGbyr5i00RlfuEyJrxiaDnrmVr0fyOeglNGzenjSptEu','莹莹','/admin/images/account/portrait/default/avatar.jpg','0',NULL,NULL,'13788889999','123827312@qq.com',NULL,'2023-11-23 01:35:44.258000','2023-11-23 01:35:44.258000',NULL,1);

/*Table structure for table `sys_user_dept` */

DROP TABLE IF EXISTS `sys_user_dept`;

CREATE TABLE `sys_user_dept` (
  `id` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `dept_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_dept` */

insert  into `sys_user_dept`(`id`,`user_id`,`dept_id`) values ('39ea7d6f135bdcdb3fbb71defb6b4683',1,1),('a5390b528a18892686b89bb593b1d050',8,1);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values ('37f9b5a72b5f42afbb5aa37ef4a86239',1,1),('9f3c131765d74bbf843c69d6398bcdd1',9,2),('d7d3b52f5d5e4281afa2c119aa7d0b83',8,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
