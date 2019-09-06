DROP table if exists email_info;
CREATE TABLE email_info (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
  disabled tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志,1删除,0正常使用',
  user_type int(10) NOT NULL DEFAULT 0 COMMENT '接收人所属的项目类别:0不分类,1小巨人,2科技企业',
  mail_host varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱主机名',
  mail_port int(10) NOT NULL DEFAULT 25 COMMENT '邮箱连接端口号',
  mail_protocol varchar(20) NOT NULL DEFAULT 'smtp' COMMENT '邮箱主机名',
  mail_auth tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否认证:1是0否',
  mail_check tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否检查:1是0否',
  send_user varchar(20) NOT NULL DEFAULT '' COMMENT '发送用户名',
  send_pass varchar(50) NOT NULL DEFAULT '' COMMENT '发送用户密码(1.腾讯生成授权码)',
  send_from varchar(500) NOT NULL DEFAULT '' COMMENT '邮件发送者',
  send_nick varchar(50) NOT NULL DEFAULT '' COMMENT '邮件发送昵称',
  send_to varchar(500) NOT NULL DEFAULT '' COMMENT '发送给谁(使用";"分隔)',
  to_nicks varchar(500) NOT NULL DEFAULT '' COMMENT '发送给昵称(使用";"分隔)',
  cc_to varchar(500) NOT NULL DEFAULT '' COMMENT '抄送给谁(使用";"分隔)',
  cc_nicks varchar(500) NOT NULL DEFAULT '' COMMENT '抄送给昵称(使用";"分隔)',
  bcc_to varchar(500) NOT NULL DEFAULT '' COMMENT '密送给谁(使用";"分隔)',
  bcc_nicks varchar(500) NOT NULL DEFAULT '' COMMENT '密送给昵称(使用";"分隔)',
  files_path varchar(500) NOT NULL DEFAULT '' COMMENT '抄送文件路径(使用";"分隔)',
  subject varchar(500) NOT NULL DEFAULT '' COMMENT '邮件主题',
  content varchar(500) NOT NULL DEFAULT '' COMMENT '邮件内容',
  create_user varchar(20) DEFAULT '' COMMENT '创建者',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件信息管理表';