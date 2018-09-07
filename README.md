## zhuozun-master
  此项目基于spring boot 构建的 spring could 微服务架构的系统，目前包含了eureka发现服务、config配置服务
  、监控系统admin、其他服务会根据业余时间不定时更新添加。
  
  该项目整合了redis memcached 两种nosql数据库，以及关系型数据库mysql；
  整合了redis基于发布订阅的轻量级消息队列；
  整合了mybatis 以及其代码生成工具；
  整合了Hystrix 接口熔断器...等等；
  包含了全局异常处理、httpclient 连接池整合、跨域资源请求处理等。
  
### 项目结构

``` java
zhuozun
├
├── zhuozun-admin-server -- 服务监控
|
├── zhuozun-api -- api接口，提供feign接口
|
├── zhuozun-cache-api -- 缓存接口api，提供feign接口
|
├── zhuozun-cache-provider -- 缓存服务（memcached、redis）提供者服务
|
├── zhuozun-common -- 公共模块各种utils
|
├── zhuozun-config-file -- 存放config配置服务的配置文件路径
|
├── zhuozun-config-server -- 配置服务中心
|
├── zhuozun-cors -- 跨域请求依赖支持
|
├── zhuozun-dao -- data access object 模块mapper xml,entity都在这里
|
├── zhuozun-discoverer -- eureka搭建的服务发现
|
├── zhuozun-exception -- 全局异常（验证）处理依赖
|
├── zhuozun-memcached -- memcached 配置依赖
|
├── zhuozun-product-provider -- 业务生产提供者服务
|
├── zhuozun-redisi -- redis 服务配置依赖
|
├── zhuozun-security -- 项目安全验证依赖（aop切面日志存储）
|
├── zhuozun-ucenter-provider -- 业务服务用户中心提供者
|
├── zhuozun-gateway-server -- zuul服务网关
|
├── zhuozun-kafka-provider -- kafka服务提供
``` 

### 涉及到的编程语言及技术框架
技术 | 说明 | 官网
----|------|----
Spring cloud eureka | 云端服务发现，一个基于 REST 的服务，用于定位服务，以实现云端中间层服务发现和故障转移。 | [https://projects.spring.io/spring-cloud/](https://projects.spring.io/spring-cloud/)
Spring cloud config server | 让你可以把配置放到远程服务器，集中化管理集群配置，目前支持本地存储、Git以及Subversion  | [https://projects.spring.io/spring-cloud/](https://projects.spring.io/spring-cloud/)
Spring cloud zuul | Zuul 是在云平台上提供动态路由,监控,弹性,安全等边缘服务的框架  | [https://projects.spring.io/spring-cloud/](https://projects.spring.io/spring-cloud/)
Spring boot admin | 服务监控  | [http://projects.spring.io/spring-boot/](http://projects.spring.io/spring-boot/)
Hystrix | 熔断器，容错管理工具，旨在通过熔断机制控制服务和第三方库的节点,从而对延迟和故障提供更强大的容错能力。 | 
Feign | 一种声明式、模板化的HTTP客户端。
MyBatis Generator | 代码生成  | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
Redis | 分布式缓存数据库  | [https://redis.io/](https://redis.io/)
Log4J | 日志组件  | [http://logging.apache.org/log4j/1.2/](http://logging.apache.org/log4j/1.2/)
Swagger2 | 接口测试框架  | [http://swagger.io/](http://swagger.io/)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)

#### 编程语言
- java 1.8.0_161

#### 主要技术框架及选型
- redis     4.0.8

- memcached     1.4.4

- spring-cloud-dependencies    Finchley.RC1

- spring-boot-admin     2.0.1

- spring-cloud     2.0.1.RELEASE

- kafka     kafka_2.11-1.0.1

### 环境搭建

> 开发环境

- 1、虚拟机（或本机）安装Jdk8、Mysql并**启动相关服务**，使用默认配置默认端口即可
- 2、克隆源代码到本地并打开，**推荐使用IntelliJ IDEA**

> 准备工作

- 创建数据库，导入zhuozun-dao项目中的*_init.sql

- 修改各服务中的对应的配置信息（mysql、redis、memcached）

> 启动服务

- 启动服务有两种方式：

- 1、执行spring boot main方法

- 2、执行maven打包命令 mvn clean install -Dmaven.test.skip=true ,jar包会被打到target目录下，进入该目录，执行 java -jar zhuozun-xxxxx-xxxx.jar --spring.profiles.active=dev

> 启动顺序

- 优先启动zhuozun-discoverer,zhuozun-config-server（如果你需要远程配置中心，不需要可以不起）。其他服务顺序随意

### 结束语
> 该项目为本人在工作学习中对spring cloud微服务架构的应用实践积累，能力有限，
有什么不对的地方希望大家不吝赐教。
