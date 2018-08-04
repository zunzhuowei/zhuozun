CREATE TABLE `goods_category` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类表';

CREATE TABLE `goods_brand` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌表';

CREATE TABLE `goods_spu` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `spu_no` varchar(50) NOT NULL COMMENT '商品编号，唯一',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名称',
  `low_price` decimal(9,2) NOT NULL COMMENT '最低售价',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `brand_id` bigint(20) NOT NULL COMMENT '品牌id',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uk_spu_no` (`spu_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='spu表';

CREATE TABLE `goods_spec` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `spec_no` varchar(50) NOT NULL COMMENT '规格编号',
  `spec_name` varchar(50) NOT NULL COMMENT '规格名称',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规格表';

CREATE TABLE `goods_spec_value` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `spec_id` bigint(20) NOT NULL COMMENT '规格id',
  `spec_value` varchar(50) NOT NULL COMMENT '规格值',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规格值表';

CREATE TABLE `goods_spu_spec` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `spu_id` bigint(20) NOT NULL COMMENT 'spu_id',
  `spec_id` bigint(20) NOT NULL COMMENT 'spec_id',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='spu规格表';

CREATE TABLE `goods_sku` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `sku_no` varchar(50) NOT NULL COMMENT 'sku编号,唯一',
  `sku_name` varchar(50) NOT NULL COMMENT 'sku名称(冗余spu_name)',
  `price` decimal(9,2) NOT NULL COMMENT '售价',
  `stock` int(11) NOT NULL COMMENT '库存',
  `shop_id` bigint(20) NOT NULL COMMENT '商铺id,为0表示自营',
  `spu_id` bigint(20) NOT NULL COMMENT 'spu_id',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sku表';

CREATE TABLE `shop_info` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `shop_name` varchar(50) NOT NULL COMMENT '店铺名称',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺表';

CREATE TABLE `goods_safeguard` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `safeguard_name` varchar(50) NOT NULL COMMENT '保障名称',
  `price` decimal(9,2) NOT NULL COMMENT '保障价格',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='增值保障';

CREATE TABLE `goods_sku_safeguard` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `sku_id` bigint(20) NOT NULL COMMENT 'sku_id',
  `safeguard_id` bigint(20) NOT NULL COMMENT 'safeguard_id',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sku增值保障';

CREATE TABLE `goods_sku_spec_value` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `spu_id` bigint(20) NOT NULL COMMENT 'sku_id',
  `spec_value_id` bigint(20) NOT NULL COMMENT '规格值id',
  `gmt_create` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sku规格值';
