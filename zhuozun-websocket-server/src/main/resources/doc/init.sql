CREATE TABLE `kun` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '鲲的名字',
  `score` bigint(16) NOT NULL COMMENT '每秒产分值',
  `type` int(11) NOT NULL COMMENT '鲲的类型',
  `ext1` varchar(255) DEFAULT NULL COMMENT '拓展1',
  `ext2` varchar(255) DEFAULT NULL COMMENT '拓展2',
  `ext3` varchar(255) DEFAULT NULL COMMENT '拓展3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='鲲表';


CREATE TABLE `user_kun_pool` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL COMMENT '玩家mid',
  `type` int(11) NOT NULL COMMENT '鲲的类型',
  `position` int(11) NOT NULL COMMENT '鲲在鲲池的位置',
  `is_run` int(3) DEFAULT '0' COMMENT '鲲是否工作',
  `run_time` int(11) DEFAULT NULL COMMENT '最近工作开始时间',
  `ext1` varchar(255) DEFAULT NULL COMMENT '拓展1',
  `ext2` varchar(255) DEFAULT NULL COMMENT '拓展2',
  `ext3` varchar(255) DEFAULT NULL COMMENT '拓展3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户鲲池表';


CREATE TABLE `user_kun_gold` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL COMMENT '玩家mid',
  `gold` bigint(16) NOT NULL COMMENT '用户金币数',
  `ext1` varchar(255) DEFAULT NULL COMMENT '拓展1',
  `ext2` varchar(255) DEFAULT NULL COMMENT '拓展2',
  `ext3` varchar(255) DEFAULT NULL COMMENT '拓展3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户鲲金币表';

CREATE TABLE `kun_gold_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(10) unsigned NOT NULL COMMENT '用户ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '日志类型',
  `gold` bigint(20) NOT NULL COMMENT '金币动态',
  `nowgold` bigint(20) unsigned NOT NULL COMMENT '现存金币',
  `action` tinyint(3) unsigned NOT NULL COMMENT '0加1减',
  `time` int(11) unsigned NOT NULL COMMENT '时间戳',
  `date` date NOT NULL DEFAULT '0000-00-00' COMMENT '添加日期',
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '日期',
  `ext1` varchar(255) DEFAULT NULL COMMENT '拓展1',
  `ext2` varchar(255) DEFAULT NULL COMMENT '拓展2',
  `ext3` varchar(255) DEFAULT NULL COMMENT '拓展3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=150147 DEFAULT CHARSET=utf8 DELAY_KEY_WRITE=1 COMMENT='用户金币流入表';

CREATE TABLE `user_kun_buy_log` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL COMMENT '玩家mid',
  `type` int(11) NOT NULL COMMENT '鲲的类型',
  `num` bigint(16) NOT NULL COMMENT '历史总购买条数',
  `ext1` varchar(255) DEFAULT NULL COMMENT '拓展1',
  `ext2` varchar(255) DEFAULT NULL COMMENT '拓展2',
  `ext3` varchar(255) DEFAULT NULL COMMENT '拓展3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户购买鲲日志表';

-- user_kun_can_buy
CREATE TABLE `user_kun_can_buy` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL COMMENT '玩家mid',
  `type` int(11) NOT NULL COMMENT '鲲的类型',
  `ext1` varchar(255) DEFAULT NULL COMMENT '拓展1',
  `ext2` varchar(255) DEFAULT NULL COMMENT '拓展2',
  `ext3` varchar(255) DEFAULT NULL COMMENT '拓展3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户可购买鲲表';
