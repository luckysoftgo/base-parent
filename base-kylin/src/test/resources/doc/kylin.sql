
SET SQL_SAFE_UPDATES = 0;

-- ----------------------------
-- 0.库的建立
-- ----------------------------
DROP DATABASE if exists data_process;
CREATE DATABASE data_process DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use data_process;

-- ----------------------------
-- 1、监控表
-- ----------------------------
drop table if exists data_monitor;
create table data_monitor (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
  disabled tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志,1删除,0正常使用',
  create_user varchar(20) DEFAULT '' COMMENT '创建者',
  create_time datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  update_user varchar(20) DEFAULT '' COMMENT '更新者',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

  project_name         varchar(50)      default ''    comment '项目名称',
  cube_name            varchar(50)      default ''    comment 'cube名称',
  table_name           varchar(50)      default ''    comment '表名称',
  schem_name           varchar(50)      default ''    comment 'schem名称',
  processing_dttm      varchar(50)      default ''    comment '时间戳',
  primary key (id)
) ENGINE=InnoDB auto_increment=0 DEFAULT CHARSET=utf8mb4 COMMENT='数据监控';
