
-- mysql 版本是 5.5 以上 , 使用如下sql:
DROP table if exists dubbo_invoke;
CREATE TABLE dubbo_invoke (
	id varchar(20) NOT NULL COMMENT '主键',
	disabled tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志,1删除,0正常使用',
	uuid varchar(45) NOT NULL DEFAULT '' COMMENT '系统生成的一个随机码',
	info_desc text COMMENT '描述信息',
	create_user varchar(20) DEFAULT '' COMMENT '创建者',
	create_time datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
	update_user varchar(20) DEFAULT '' COMMENT '更新者',
	update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	service varchar(50) NOT NULL DEFAULT '' COMMENT '服务名称',
	method varchar(50) NOT NULL DEFAULT '' COMMENT '方法名称',
	consumer varchar(50) NOT NULL DEFAULT '' COMMENT '消费者',
	provider varchar(50) NOT NULL DEFAULT '' COMMENT '消费者',
	type varchar(50) NOT NULL DEFAULT '' COMMENT '调用类型',
	invoke_time BIGINT(20) NOT NULL DEFAULT 0 COMMENT '调用次数',
	invoke_date datetime NOT NULL DEFAULT NOW() COMMENT '调用时间',
	success INT(12) NOT NULL DEFAULT 0 COMMENT '成功次数',
	failure INT(12) NOT NULL DEFAULT 0 COMMENT '失败次数',
	elapsed INT(12) NOT NULL DEFAULT 0 COMMENT '逝去次数',
	concurrent INT(12) NOT NULL DEFAULT 0 COMMENT '并发次数',
	max_elapsed INT(12) NOT NULL DEFAULT 0 COMMENT '最大逝去次数',
	max_concurrent INT(12) NOT NULL DEFAULT 0 COMMENT '最大并发次数',
	PRIMARY KEY (id),
	KEY index_service (service) USING BTREE,
	KEY index_method (method) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控详情记录';

-- mysql 版本是 5.5 以下 , 使用如下sql:

DROP table if exists dubbo_invoke;
CREATE TABLE dubbo_invoke (
	id varchar(20) NOT NULL COMMENT '主键',
	disabled tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志,1删除,0正常使用',
	uuid varchar(45) NOT NULL DEFAULT '' COMMENT '系统生成的一个随机码',
	info_desc text COMMENT '描述信息',
	create_user varchar(20) DEFAULT '' COMMENT '创建者',
	create_time datetime COMMENT '创建时间',
	update_user varchar(20) DEFAULT '' COMMENT '更新者',
	update_time datetime COMMENT '更新时间',
	service varchar(50) NOT NULL DEFAULT '' COMMENT '服务名称',
	method varchar(50) NOT NULL DEFAULT '' COMMENT '方法名称',
	consumer varchar(50) NOT NULL DEFAULT '' COMMENT '消费者',
	provider varchar(50) NOT NULL DEFAULT '' COMMENT '消费者',
	type varchar(50) NOT NULL DEFAULT '' COMMENT '调用类型',
	invoke_time BIGINT(20) NOT NULL DEFAULT 0 COMMENT '调用次数',
	invoke_date datetime COMMENT '调用时间',
	success INT(12) NOT NULL DEFAULT 0 COMMENT '成功次数',
	failure INT(12) NOT NULL DEFAULT 0 COMMENT '失败次数',
	elapsed INT(12) NOT NULL DEFAULT 0 COMMENT '逝去次数',
	concurrent INT(12) NOT NULL DEFAULT 0 COMMENT '并发次数',
	max_elapsed INT(12) NOT NULL DEFAULT 0 COMMENT '最大逝去次数',
	max_concurrent INT(12) NOT NULL DEFAULT 0 COMMENT '最大并发次数',
	PRIMARY KEY (id),
	KEY index_service (service) USING BTREE,
	KEY index_method (method) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控详情记录';