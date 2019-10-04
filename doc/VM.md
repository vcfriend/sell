# 虚拟机说明文档
VirtualBox-5.1.22
虚拟机系统 centos7.3
账号 root
密码 123456

#centos常用命令
 ifconfig #查看ip地址 <br>
 chkconfig --list | grep on    # 列出所有启动的系统服务程序 <br>
 service iptables status  # 查看防火墙状态<br>
 chkconfig iptables off #关闭防火墙(重启后永久生效)<br>
 service iptables stop 关闭防火墙(即时生效，重启后失效) <br>
如何让程序开机时自动启动<br>
启动项 /etc/rc.local
这是一个最简单的方法，编辑“/etc/rc.local”，把启动程序的shell命令输入进去即可（要输入命令的全路径），类似于windows下的“启动”。
使用命令 vi  /etc/rc.local   
然后在文件最后一行添加要执行程序的全路径。
例如，每次开机时要执行一个haha.sh，这个脚本放在/opt下面，那就可以在“/etc/rc.local”中加一行“/opt/./haha.sh”，或者两行“cd /opt”和“./haha.sh”。


#### 包括软件
* jdk 1.8.0_111
* nginx 1.11.7
* mysql 5.7.17
* redis 3.2.8

##### jdk
* 路径 /usr/local/jdk1.8.0_111

##### nginx
* 路径 /usr/local/nginx
* 启动 nginx
* 重启 nginx -s reload

##### mysql
* 配置 /etc/my.conf
* 账号 root
* 密码 123456
* 端口 3306
* 启动 systemctl start mysqld
* 停止 systemctl stop mysqld

##### redis
* 路径 /usr/local/redis
* 配置 /etc/reis.conf
* 端口 6379
* 密码 123456
* 启动 systemctl start redis
* 停止 systemctl stop redis

##### tomcat
* 路径 /usr/local/tomcat
* 启动 systemctl start tomcat
* 停止 systemctl stop tomcat


