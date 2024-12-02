#!/bin/bash
# 清除之前运行的控制台输出日志
echo "">run.jar.out
# 指定jdk的 java 命令，你也可以指定对应的 jdk 版本
java=env/linux/jdk-17.0.5/bin/java
# 打包好后的jar包名，每个服务的 jar 包名不一样
jar=$1
echo "*****************尝试重启中*****************"
# 先杀进程
oldpid=`env/linux/jdk-17.0.5/bin/jps | grep $jar | grep -v "prep" | awk '{print $1}'`
kill -9 $oldpid
if [ "$?" -eq 0 ]; then
    echo "kill 成功，pid：$oldpid"
else
    echo "kill 失败，没有找到对应的进程"
fi
# 配置 VM 参数
vm=-Dfile.encoding=utf-8 \
-Dmaven.wagon.http.ssl.insecure=true \
-Dmaven.wagon.http.ssl.allowall=true \
--add-opens java.base/java.util=ALL-UNNAMED \
--add-opens java.base/java.lang=ALL-UNNAMED \
--add-opens java.base/java.lang.reflect=ALL-UNNAMED \
--add-opens java.base/java.lang.invoke=ALL-UNNAMED \
--add-opens java.base/java.lang.io=ALL-UNNAMED
# 配置 Jar 包参数
params=--spring.profiles.active=test
echo "启动中：$jar"
# 组合成启动命令，后台运行，并且把控制台日志输出到 run.jar.out 文件
nohup $java $vm -jar $jar $params >run.jar.out 2>&1 &
nowpid=`jps | grep $jar | grep -v "prep" | awk '{print $1}'`
echo "*****************启动成功，pid：$nowpid"*****************"