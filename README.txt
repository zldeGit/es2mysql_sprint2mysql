@author: lzk
@company:zkwg
@程序功能：青铜峡中的稿件程序从es同步到公网mysql数据库中，供其他程序同步到闻海es当中。
@启动过程：执行maven install 把项目对应文件(bin,conf,lib,logs)拷贝到服务器上,执行bin/下的“startup.sh”脚本。

@部署文件夹说明：
    bin:存放脚本文件
        startup.sh      启动脚本
        stop.sh         停止脚本
        setenv.sh       设置环境变量脚本（如设置jdk,该程序需配置jdk1.7）
          配置classpath脚本
    conf:配置文件
    lib:依赖jar包
    logs:日志文件

@不同融媒体配置，修改conf下的push.properties，一般需修改的属性如下：
    #记录上次推送时间，修改为0，可以重新推送
    pushTime=1546996519000
    #查询的es索引
    esIndexs=sprint_mixmedia_product
    #es IP 地址
    esServerIp=192.168.16.15
    #es 集群名
    esClusterName=sprint-product-16-15
