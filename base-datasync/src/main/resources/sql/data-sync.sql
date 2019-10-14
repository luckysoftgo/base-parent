
-- SET SQL_SAFE_UPDATES = 0;

-- ----------------------------
-- 0.库的建立
-- ----------------------------
-- DROP DATABASE if exists data_sync;
-- CREATE DATABASE data_sync DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- use data_sync;

-- ----------------------------
--  处理api记录数据的信息
-- ----------------------------
DROP TABLE IF EXISTS request_info_sign;
CREATE TABLE request_info_sign  (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
  disabled tinyint(1)  DEFAULT 0 COMMENT '删除标志,1删除,0正常使用',
  companyId varchar(50) DEFAULT '' COMMENT '工业库企业Id',
  createBy varchar(20) DEFAULT '' COMMENT '创建者',
  createTime datetime  DEFAULT NOW() COMMENT '创建时间',

  apiKey varchar(50) DEFAULT '' COMMENT '请求的apiKey',
  apiName varchar(50) DEFAULT '' COMMENT '请求的接口描述',
  requestUrl varchar(100) DEFAULT '' COMMENT '请求的url',
  json text COMMENT 'json内容',
  primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '处理api记录数据的信息' ;
