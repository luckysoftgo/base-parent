maven 仓库:
https://mvnrepository.com/

本框架是:集合 Spring + SpringMVC + Mybatis + Mongo + Redis + MQ(消息) + Pay(支付) 于一体的集合框架;
方便开发者在使用过程中,对已经确定的业务,进行对应的企业级开发操作。

base-api-demo : 是对本框架上的操作实现;
可以方便使用人员根据详细的了解如何使用本框架来进行企业级的开发操作.


目前框架缺陷：
若表的主键是 String 类型,无法使用框架的:deleteObjectByID 方法进行数据的逻辑或者物理删除.
可以使用:deleteObjectByUUID 方法来实现对数据的物理或逻辑删除操作.



一. Idea 在Maven模式下,打不开WEB项目的原因 , 是需要配置相关的启动配置:

具体配置是: tomcat ——> edit configurations ——>
弹框出现tomcat的配置页面 ——> 选中Deployment,编辑相关信息,Add 选中项目,带xxx:war exploded,Application context:/xxx ——>
F12(点击铅笔),编辑xxx:war exploded的相关信息 ——> Output directory(target 生成的目录) ——>
左边(编辑Facets),设置访问的WEB目录 ——> 在Web Resource Directory目录下编辑项目的 Web目录 ——> 点击OK,保存设置 ——> 启动tomcat


二.操作数据的工具,可以做参考!
https://github.com/winstonelei/BigDataTools