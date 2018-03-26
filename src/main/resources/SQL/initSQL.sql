#--MySQL 5.5版本
#--商品表
CREATE TABLE IF NOT EXISTS `product_info`(
	`product_id` VARCHAR(32) NOT NULL,
	`product_name` VARCHAR(64) NOT NULL COMMENT '商品名称',
	`product_price` DECIMAL(8,2) NOT NULL COMMENT '单价',
	`product_stock` INT NOT NULL COMMENT '库存',
	`product_description` VARCHAR(64) COMMENT '描述',
	`product_status` TINYINT(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
	`product_icon` VARCHAR(512) COMMENT '小图',
	`category_type` INT NOT NULL COMMENT '类目编号',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	#`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`update_time` TIMESTAMP NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`product_id`)
) COMMENT '商品表';

#--可以使用触发器来替代下
#--解决: [Err] 1293 - Incorrect table definition; there can be only one TIMESTAMP column with CURRENT_TIMESTAMP in DEFAULT or ON UPDATE clause
DROP TRIGGER IF EXISTS `update_product_info_trigger`;
DELIMITER //
CREATE TRIGGER `update_product_info_trigger` BEFORE UPDATE ON `product_info`
 FOR EACH ROW SET NEW.`update_time` = NOW();
//
DELIMITER ;
#--类目表
CREATE TABLE IF NOT EXISTS `product_category` (
	`category_id` INT NOT NULL auto_increment,
	`category_name` VARCHAR(64) NOT NULL COMMENT '类目名字',
	`category_type` INT NOT NULL COMMENT '类目编号',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	#`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间表',
	`update_time` TIMESTAMP NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`category_id`),
	UNIQUE KEY `uqe_category_type` (`category_type`)
) COMMENT '类目表';
#--可以使用触发器来替代下
#- 触发器的使用详解-http://blog.csdn.net/baidu_30000217/article/details/50466346
#--解决: [Err] 1293 - Incorrect table definition; there can be only one TIMESTAMP column with CURRENT_TIMESTAMP in DEFAULT or ON UPDATE clause
DROP TRIGGER IF EXISTS `update_product_category_trigger`;
DELIMITER //
CREATE TRIGGER `update_product_category_trigger` BEFORE UPDATE ON `product_category`
 FOR EACH ROW SET NEW.`update_time` = NOW();
//
DELIMITER ;

#-- 订单表
CREATE TABLE IF NOT EXISTS `order_master` (
	`order_id` VARCHAR(32) NOT NULL,
	`buyer_name` VARCHAR(32) NOT NULL COMMENT '买家名字',
	`buyer_phone` VARCHAR(32) NOT NULL COMMENT '买家电话',
	`buyer_address` VARCHAR(128) NOT NULL COMMENT '买家地址',
	`buyer_openid` VARCHAR(64) NOT NULL COMMENT '买家微信openid',
	`order_amount` DECIMAL(8,2) NOT NULL COMMENT '订单总金额',
	`order_status` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '订单状态,默认0新下单',
	`pay_status` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '支付状态,默认0未支付',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	#`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`update_time` TIMESTAMP NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`order_id`),
	KEY `idx_buyer_openid` (`buyer_openid`)
) COMMENT '订单表';
#--可以使用触发器来替代下
#- 触发器的使用详解-http://blog.csdn.net/baidu_30000217/article/details/50466346
#--解决: [Err] 1293 - Incorrect table definition; there can be only one TIMESTAMP column with CURRENT_TIMESTAMP in DEFAULT or ON UPDATE clause
DROP TRIGGER IF EXISTS `update_order_master_trigger`;
DELIMITER //
CREATE TRIGGER `update_order_master_trigger` BEFORE UPDATE ON `order_master`
 FOR EACH ROW SET NEW.`update_time` = NOW();
//
DELIMITER ;


-- 订单商品
create table `order_detail` (
    `detail_id` varchar(32) not null,
    `order_id` varchar(32) not null,
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '当前价格,单位分',
    `product_quantity` int not null comment '数量',
    `product_icon` varchar(512) comment '小图',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    #`update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
		`update_time` TIMESTAMP NOT NULL COMMENT '更新时间',
    primary key (`detail_id`),
    key `idx_order_id` (`order_id`)
);
#--可以使用触发器来替代下
#- 触发器的使用详解-http://blog.csdn.net/baidu_30000217/article/details/50466346
#--解决: [Err] 1293 - Incorrect table definition; there can be only one TIMESTAMP column with CURRENT_TIMESTAMP in DEFAULT or ON UPDATE clause
DROP TRIGGER IF EXISTS `update_order_detail_trigger`;
DELIMITER //
CREATE TRIGGER `update_order_detail_trigger` BEFORE UPDATE ON `order_detail`
 FOR EACH ROW SET NEW.`update_time` = NOW();
//
DELIMITER ;

-- 卖家(登录后台使用, 卖家登录之后可能直接采用微信扫码登录，不使用账号密码)
create table `seller_info` (
    `id` varchar(32) not null,
    `username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null comment '微信openid',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    #`update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
		`update_time` TIMESTAMP NOT NULL COMMENT '更新时间',
    primary key (`id`)
) comment '卖家信息表';
#--可以使用触发器来替代下
#- 触发器的使用详解-http://blog.csdn.net/baidu_30000217/article/details/50466346
#--解决: [Err] 1293 - Incorrect table definition; there can be only one TIMESTAMP column with CURRENT_TIMESTAMP in DEFAULT or ON UPDATE clause
DROP TRIGGER IF EXISTS `update_seller_info_trigger`;
DELIMITER //
CREATE TRIGGER `update_seller_info_trigger` BEFORE UPDATE ON `seller_info`
 FOR EACH ROW SET NEW.`update_time` = NOW();
//
DELIMITER ;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (NULL, '男生最爱', '2', NULL, NULL);
INSERT INTO `product_category` VALUES (NULL, '女生最爱', '3', '2018-03-24 22:18:26', NULL);
INSERT INTO `product_category` VALUES ('4', '热销榜', '1', '2018-03-25 15:41:47', '2018-03-24 22:18:33');
INSERT INTO `product_category` VALUES ('5', '主食', '11', '2018-03-25 16:44:56', '2018-03-25 16:54:59');

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('1', '皮蛋粥', '3.50', '100', '很好吃的粥', 'http://xxxx.jpg', '0', '2', '2018-03-26 02:14:09', '2018-03-26 02:14:09');
INSERT INTO `product_info` VALUES ('2', '茶叶蛋', '1.50', '100', '好吃又有营养的蛋蛋', 'http://xxxx.jpg', '0', '3', '2018-03-26 02:14:45', '2018-03-26 02:14:45');
INSERT INTO `product_info` VALUES ('3', '蛋炒饭', '6.00', '100', '美味好吃蛋炒饭', 'http://bbb.jpg', '0', '3', '2018-03-26 02:15:05', '2018-03-26 02:15:05');
INSERT INTO `product_info` VALUES ('4', '番茄炒蛋', '10.00', '100', '真是太好吃了', 'http://ccc.jpg', '0', '3', '2018-03-26 02:15:05', '2018-03-26 02:15:05');
