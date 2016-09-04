
# quant4s
一个用scala 实现的交易平台系统，支持多语言编写策略，支持回测,支持实盘

# 简介
引擎采用Scala 语言，基于Akka 架构编写，并完全采用开源化管理方案。系统可以构建在桌面系统和云服务器上。
设计目标是采用Actor模型，提供一种大数据下，高并发计算的股票算法交易方案。系统采用JDK1.8，支持LINUX, WINDOWS, MAC OS等平台。
引擎分为服务器端和客户端两个部分组成。客户端请参见[quant4s-sdk][quant4s-sdk-href]。

# 部署方法
1. 安装zeroMQ， 参见 http://zeromq.org/ ,
2. 克隆，编译，运行项目


 ```
 git clone https://github.com/quant4s/quant4s
 mvn compile
 sh run.sh [linux]
 ```

[quant4s-sdk-href]: https://github.com/quant4s/quant4s-sdk "SDK"
