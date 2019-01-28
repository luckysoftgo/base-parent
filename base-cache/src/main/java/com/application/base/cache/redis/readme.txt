
redis 存储缓存数据的操作实现.

基于配置文件的 redis 实现.
只提供了基本的redis 的操作，更多的操作现在没有提供，
详细地址 : http://tool.oschina.net/uploads/apidocs/ 

redis : 中文官网描述
http://redis.cn/commands/expire.html
#重要命令使用:
http://doc.redisfans.com/
https://www.yiibai.com/redis/lists_rpoplpush.html
https://www.cnblogs.com/kongzhongqijing/p/6867960.html

学习redis的资料.
htp://www.iocoder.cn/Fight/Redis-went-from-getting-started-to-quitting



应用描述:
1.redis中,hash键的意义何在?
    a.Hash键可以将信息凝聚在一起,而不是直接分散的存储在整个redis中,不仅仅方便数据管理,还可以避免一定的误操作;
    b.避免键名冲突;
    c.减少内存使用;
2.不适合使用Hash键的情况?
    a.拥有过期功能的使用,过期功能只能使用key上;
    b.二进制操作命令：setbit、getbit、bitop;
    c.需要考虑数据分布问题;

3.基于集合键,实现直播刷礼物,转发微博等抽奖活动
    a.sadd KEY {userID} 刷礼物,转发微博加入到集合键中;点赞
    b.smembers KEY 获取所有用户,大转盘转起来;
    c.spop key [count] / srandmember key [count] 抽取 count 名中奖者.
    d.srem 取消点赞;
    e.检查用户是否点过赞:sismember
    f.获取点赞的用户列表:smembers
    g.获取点赞用户数: scard




































