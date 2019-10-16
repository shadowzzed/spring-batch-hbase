# 技术选型
* 基础框架 spring-boot
* 批处理框架 spring-batch
* 数据库 hbase

# processor设计思路
* 维护两张表 当表容量到达一定程度则发送给writer
* 一张是存储了request请求的表用来和response请求匹配
* 一张存储了response与request匹配后生成的对象
<br>**存在一个问题：如果请求和响应间隔的时间超长则会造成请求已经发送到writer但是响应才刚到的情况**

