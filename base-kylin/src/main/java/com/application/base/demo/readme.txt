DEMO 包是使用连接池的一个实际使用实例;
DEMO 的用例重点如下:

1.使用 xxl-job 做定时任务调度;

2.基于kylin提供的 rest api 和 jdbc方式,获取数据;

3.用 xxl-job 的任务调度,实时监控 kylin中的数据,并将 kylin 中 cube 变化的数据抽取出来,放到 elasticsearch 上去,然后再用 kibana 做展示.

4.CubesInfoTask.java 和 CubeDescInfoTask.java 是对"3"功能的实现,监控数据的运行和数据增量的变化.

代码要在一个新的springboot工程中才能够很好的运行,否则可能无法运行起来 !
