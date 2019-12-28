http://hadoop.apache.org/docs/r3.2.1/hadoop-project-dist/hadoop-common/SingleCluster.html

如下的命令,都必须在 hadoop 的机器上执行

1.获取 hdfs 的地址命令:
    hdfs getconf -confKey fs.default.name

2.获取 hadoop 下文件的命令:
    hadoop  fs -ls /
    hadoop  fs -ls /tmp/

3.删除文件命令:
    bin/hdfs dfs -rm output2/*

4.删除文件夹命令:
    bin/hdfs dfs -rm -r output2

5.抓取内容命令:
    bin/hdfs dfs -cat /user/output1/part-r-00000

6.传文件到 hdfs 中去命令:
    bin/hdfs dfs -put LICENSE.txt

7.传文件到 hdfs 的某个文件夹中去命令:
    bin/hdfs dfs -put LICENSE.txt input2

8.将 hdfs 中的output文件夹复制到本地文件目录的output文件夹中命令:
    bin/hdfs dfs -get output output