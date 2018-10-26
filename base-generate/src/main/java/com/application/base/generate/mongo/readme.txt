
generate 包下是生成代码的工具类.

javabase 负责生成 jdbc 下的连接 javabean .

mongo 负责生成 mongo 下的连接 javabean .

备注 :
 
1.代码生成器对应的表名,必须要有"_"作为表的连接.eg: test_teacher,test_student;

2.数据库设计上如果设计到逻辑删除，用 int 类型的disabled标识,还有就是必须有create_time字段用来标识创建时间。

3.设计数据库的样例表建表语句:

use database_name;
drop table if exists table_name;
create table table_name(
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键', ##要是手动插入的主键,则用字符串标识,并在生成代码的generate.properties 中将generate.primaryKeyStyle=MAN
  disabled tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标志,1删除,0正常使用',
  uuid varchar(40) NOT NULL DEFAULT '' COMMENT '系统生成的一个随机码', ##无序的操作
  create_time datetime NOT NULL DEFAULT NOW() COMMENT '创建时间', ##创建时间
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'  ##更新时间
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付记录日志表';

4.CommonConstant.java 中是代码生成中的常量的设置问题,需要在使用的时候注意更改文件的目录结构.

